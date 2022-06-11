import {ActionTypes} from "../constans/Action-Type";

const initialState ={
    accounts:[]
}
export const productReducer =(state = initialState,{type,payload})=>{
    switch (type){
        case ActionTypes.SET_ACCOUNT:
            return {...state,accounts: payload}
        default:
            return state
    }

}

export  const  selectedProductReducer = (state={},{type,payload}) =>{
    switch (type){
        case ActionTypes.SELECTED_ACCOUNT:
            return {...state, ...payload};
        case ActionTypes.REMOVE_SELECTED_ACCOUNT:
            return {};
        default:
            return state;
    }
}