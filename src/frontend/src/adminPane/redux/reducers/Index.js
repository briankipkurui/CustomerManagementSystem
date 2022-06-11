import { combineReducers} from "redux";
import {productReducer,selectedProductReducer} from "./ProductReducer"

 const reducers = combineReducers({
    allAccounts: productReducer,
     account:selectedProductReducer
})
export default reducers;