import {useParams} from 'react-router-dom'
import fetch from "unfetch";
import {useContext, useEffect} from "react";
import AuthContext from "../../../context/AuthContext";
import {useDispatch, useSelector} from "react-redux";
import {selectedAccount,removeSelectedAccount} from "../actions/ProductAction";
const AccountDetails = () => {
    const {accountId} = useParams()
    const {authTokens} = useContext(AuthContext)
    const dispatch = useDispatch()
    const  account = useSelector((state) =>state.account)
    const {firstName,lastName,email,phoneNumber,identificationNo} = account


    const fetchAccountDetails = async () =>{
        let response = await fetch(`management/api/v1/customer/${accountId}`,{
            method : 'GET',
            headers :{
                'content-Type':'application/json',
                'Authorization':'Bearer ' +String(authTokens.access_token)
            }
        })
        let data = await response.json()
        dispatch(selectedAccount(data))

    }
    useEffect(() =>{
      if(accountId && accountId !=="")
          fetchAccountDetails()

       return ()=>{
          dispatch(removeSelectedAccount())
       }
    },[accountId])

    return(
        <>
            <h1>{email}</h1>
            <h1>Brian</h1>
        </>
    )
}
export default AccountDetails