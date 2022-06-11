import './CreditCustomer.css'
import {useDispatch, useSelector} from "react-redux";
import ProductsComponents from "./redux/containers/ProductsComponents";
import fetch from "unfetch";
import {useContext, useEffect} from "react";
import AuthContext from "../context/AuthContext";
import {setAccount} from "./redux/actions/ProductAction";

function CreditCustomer() {
    const accounts = useSelector((state) =>state)
    let {authTokens} = useContext(AuthContext)
    const dispatch = useDispatch()
    const fetchAccount = async () =>{
        let response = await fetch("management/api/v1/customer/all",{
             method:'GET',
            headers:{
                 'content-Type':'application/json',
                  'Authorization':'Bearer '+String(authTokens.access_token)
            }
        })
        let data = await response.json()
        dispatch(setAccount(data))

    }

    useEffect(() =>{
        fetchAccount()
    },[])
    return(
        <>
            <div className="container">
                <div className="search-bar">

                    <input type="text" placeholder="search by phone-no or email"/>
                </div>

               <div className="flex-flex">
                 <ProductsComponents/>

                </div>
            </div>
        </>
    )
}
export default CreditCustomer