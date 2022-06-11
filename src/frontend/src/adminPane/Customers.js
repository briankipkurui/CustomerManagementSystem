import './Customer.css'
import { useContext, useEffect, useState} from "react";
import fetch from "unfetch";
import AuthContext from "../context/AuthContext";
import {
    LoadingOutlined, PlusOutlined,

} from '@ant-design/icons';
import {Button, Empty, Spin, Table} from "antd";
import CustomerDrawerForm from "./CustomerDrawerForm";
import AdminDrawerForm from "./AdminDrawerForm";
import AdminTraineeDrawerForm from "./AdminTraineeDrawerForm";


const checkStatus = response =>{
    if (response.ok){
        return response;
    }

    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}




const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function Customers() {
    const [customer,setCustomer] = useState([])
    const [fetching,setFetching] = useState(true)
    const [showDrawer, setShowDrawer] = useState(false)
    const [showAdminDrawer,setShowAdminDrawer] = useState(false)
    const [showAdminTraineeDrawer,setShowAdminTraineeDrawer] = useState(false)

    let {authTokens} = useContext(AuthContext)

    let getAllCustomers = async() =>{
        let response = await fetch("management/api/v1/customer/all",{
            method:'GET',
            headers:{
                'content-Type': 'application/json',
                'Authorization': 'Bearer ' +String(authTokens.access_token)
            }

        }).then(checkStatus)
        let data = await response.json()
        setCustomer(data)
        console.log('data',data)
    }

    const fetAllCustomers =()=>{
        getAllCustomers()
            .finally(()=>setFetching(false))

    }

    useEffect(()=>{
        fetAllCustomers()


    },[])

    const columns = fetCustomers =>[
        {
            title: 'Id',
            dataIndex: 'id',
            key: 'id',
        },
        {
            title: 'FirstName',
            dataIndex: 'firstName',
            key: 'firstName',
        },
        {
            title: 'LastName',
            dataIndex: 'lastName',
            key: 'lastName',
        },
        {
            title: 'ApplicationUserRole',
            dataIndex: 'applicationUserRole',
            key: 'applicationUserRole',
        },
        {
            title: 'PhoneNumber',
            dataIndex: 'phoneNumber',
            key: 'phoneNumber',
        },
        {
            title: 'Email',
            dataIndex: 'email',
            key: 'email',
        },

        {
            title: 'Location',
            dataIndex: 'location',
            key: 'location',
        },
        {
            title: 'IdentificationNo',
            dataIndex: 'identificationNo',
            key: 'identificationNo',
        },
        {
            title: 'Password',
            dataIndex: 'password',
            key: 'password',
        },

        {
            title: 'UserName',
            dataIndex: 'username',
            key: 'username',
        },

    ]


 const renderCustomers = ()=>{

   if (fetching){
       return <Spin indicator={antIcon}/>

   }

   if ((customer.length) <= 0){
       return (
           <>
               <Button
                   onClick={()=>setShowDrawer(!showDrawer)}
                   type="primary" shape="round" icon={<PlusOutlined/>} size="small"
                   style={{color: "#fff",backgroundColor: '#0B0B45'}}
               >
                   Add new Customer
               </Button>
               <Button
                   onClick={()=>setShowAdminDrawer(!showAdminDrawer)}
                   style={{marginLeft: "5px",color: "#fff",backgroundColor: '#0B0B45'}}
                   type="primary" shape="round" icon={<PlusOutlined/>} size="small"
               >
                   Add new Admin
               </Button>
               <Button
                   onClick={()=>setShowAdminTraineeDrawer(!showAdminTraineeDrawer)}
                   style={{marginLeft: "5px",color: "#fff",backgroundColor: '#0B0B45'}}
                   type="primary" shape="round" icon={<PlusOutlined/>} size="small"
               >
                   Add new AdminTrainee
               </Button>

               <CustomerDrawerForm
                   showDrawer={showDrawer}
                   setShowDrawer={setShowDrawer}
                   fetAllCustomers={fetAllCustomers}
               />

               <AdminDrawerForm
                   showAdminDrawer={showAdminDrawer}
                   setShowAdminDrawer={setShowAdminDrawer}
                   fetAllCustomers={fetAllCustomers}
               />
               <AdminTraineeDrawerForm
                   showAdminTraineeDrawer={showAdminTraineeDrawer}
                   setShowAdminTraineeDrawer={setShowAdminTraineeDrawer}
                   fetAllCustomers={fetAllCustomers}
               />


               <Empty/>

           </>
       )
   }

   return (
       <>

           <CustomerDrawerForm
               showDrawer={showDrawer}
               setShowDrawer={setShowDrawer}
               fetAllCustomers={fetAllCustomers}
           />

           <AdminDrawerForm
               showAdminDrawer={showAdminDrawer}
               setShowAdminDrawer={setShowAdminDrawer}
               fetAllCustomers={fetAllCustomers}
           />
           <AdminTraineeDrawerForm
               showAdminTraineeDrawer={showAdminTraineeDrawer}
               setShowAdminTraineeDrawer={setShowAdminTraineeDrawer}
               fetAllCustomers={fetAllCustomers}
           />


           <Table

               dataSource={customer}
               columns={columns(fetAllCustomers)}
               bordered
               title={()=>
                   <>

                          <Button
                              onClick={()=>setShowDrawer(!showDrawer)}
                              type="primary" shape="round" icon={<PlusOutlined/>} size="small"
                             style={{color: "#fff",backgroundColor: '#0B0B45'}}
                             >
                              Add new Customer
                            </Button>
                           <Button
                               onClick={()=>setShowAdminDrawer(!showAdminDrawer)}
                               style={{marginLeft: "5px",color: "#fff",backgroundColor: '#0B0B45'}}
                               type="primary" shape="round" icon={<PlusOutlined/>} size="small"
                           >
                               Add new Admin
                           </Button>
                           <Button
                               onClick={()=>setShowAdminTraineeDrawer(!showAdminTraineeDrawer)}
                               style={{marginLeft: "5px",color: "#fff",backgroundColor: '#0B0B45'}}
                               type="primary" shape="round" icon={<PlusOutlined/>} size="small"
                           >
                               Add new AdminTrainee
                           </Button>



                   </>
               }
               pagination={{pageSize: 5}}
               scroll={{x:400}}
               rowKey={(customer) => customer.id}


           />

       </>
   )
 }

    return(
        <>
           <div className="container">
                <div className="search-bar">

                    <input type="text" placeholder="search by phone-no or email"/>
                </div>
           </div>
           <section className="table-section container ">
               {renderCustomers()}
           </section>
        </>
    )

}
export default Customers