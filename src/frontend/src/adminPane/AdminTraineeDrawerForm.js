import {
    Drawer,
    Input,
    Col,
    Form,
    Row,
    Button,
    Spin
} from 'antd';
import React, {useContext,useState} from "react";
import fetch from "unfetch";
import AuthContext from "../context/AuthContext";
import {errorNotification, successNotification} from "../Notification";
import {LoadingOutlined} from "@ant-design/icons";



const checkStatus = response =>{
    if (response.ok){
        return response;
    }

    const error = new Error(response.statusText);
    error.response = response;
    return Promise.reject(error);
}
const antIcon = <LoadingOutlined style={{ fontSize: 24 }} spin />;

function AdminTraineeDrawerForm({showAdminTraineeDrawer,setShowAdminTraineeDrawer,fetAllCustomers}) {
    const onCLose = () => setShowAdminTraineeDrawer(false);
    const [submitting, setSubmitting] = useState(false);
    let {authTokens} =useContext(AuthContext)

    const addNewAdminTrainee = adminTrainee =>
        fetch("management/api/v1/customer/registerAdminTrainee", {

            headers: {
                'content-Type':'application/json',
                'Authorization': 'Bearer '+String(authTokens.access_token)
            },
            method: 'POST',
            body:JSON.stringify(adminTrainee)
        }).then(checkStatus)


    const onFinish = adminTrainee =>{
        // console.log(JSON.stringify(customer, null, 2))
        setSubmitting(true)
        addNewAdminTrainee(adminTrainee)
            .then(() =>{
                onCLose();
                successNotification(
                    "customer successfully added ",
                    `${adminTrainee.firstName} was  added to the system`
                )
                fetAllCustomers();
            }).catch(err => {
            console.log(err);
            err.response.json().then(res => {
                console.log(res);
                errorNotification(
                    "There was an issue",
                    `${res.message} [${res.status}] [${res.error}]`
                )
            });

        }).finally(()=>{
            setSubmitting(false)
        })


    }
    const onFinishFailed = errorInfo=> {
        alert(JSON.stringify(errorInfo, null, 2));
    }


    return(

        <Drawer
            title="Add new AdminTrainee"
            width={720}
            onClose={onCLose}
            visible={showAdminTraineeDrawer}
            bodyStyle={{paddingBottom: 80}}
            footer={
                <div
                    style={{
                        textAlign: 'right',
                    }}
                >
                    <Button onClick={onCLose} style={{marginRight: 8}}>
                        Cancel
                    </Button>
                </div>
            }
        >
            <Form layout="vertical"
                  onFinishFailed={onFinishFailed}
                  onFinish={onFinish}
                  hideRequiredMark>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            name="firstName"
                            label="FirstName"
                            rules={[{required: true, message: 'Please enter customer firstname'}]}
                        >
                            <Input placeholder="Please enter customer firstname"/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            name="lastName"
                            label="LastName"
                            rules={[{required: true, message: 'Please enter customer lastName'}]}
                        >
                            <Input placeholder="Please enter customer lastName"/>
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>
                    <Col span={12}>
                        <Form.Item
                            name="phoneNumber"
                            label="PhoneNumber"
                            rules={[{required: true, message: 'Please enter customer phoneNumber'}]}
                        >
                            <Input placeholder="Please enter customer phoneNumber"/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            name="email"
                            label="Email"
                            rules={[{required: true, message: 'Please enter customer email'}]}
                        >
                            <Input placeholder="Please enter customer email"/>
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>

                    <Col span={12}>
                        <Form.Item
                            name="location"
                            label="Location"
                            rules={[{required: true, message: 'Please enter customer location'}]}
                        >
                            <Input placeholder="Please enter customer location"/>
                        </Form.Item>
                    </Col>
                    <Col span={12}>
                        <Form.Item
                            name="identificationNo"
                            label="IdentificationNo"
                            rules={[{required: true, message: 'Please enter customer identificationNo'}]}
                        >
                            <Input placeholder="Please enter customer identificationNo"/>
                        </Form.Item>
                    </Col>
                </Row>
                <Row gutter={16}>

                    <Col span={12}>
                        <Form.Item
                            name="password"
                            label="Password"
                            rules={[{required: true, message: 'Please enter customer password'}]}
                        >
                            <Input placeholder="Please enter customer password"/>
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    <Col span={12}>
                        <Form.Item >
                            <Button type="primary" htmlType="submit">
                                Submit
                            </Button>
                        </Form.Item>
                    </Col>
                </Row>
                <Row>
                    {submitting && <Spin indicator={antIcon} />}
                </Row>
            </Form>
        </Drawer>

    )
}
export default AdminTraineeDrawerForm
