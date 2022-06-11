import {ActionTypes} from "../constans/Action-Type";

export const setAccount = (accounts) =>{
   return {
       type:ActionTypes.SET_ACCOUNT,
       payload:accounts
   }
}

export  const selectedAccount = (account) =>{
    return{
        type:ActionTypes.SELECTED_ACCOUNT,
        payload:account
    }
}

export const removeSelectedAccount = () =>{
    return {
        type:ActionTypes.REMOVE_SELECTED_ACCOUNT
    }
}