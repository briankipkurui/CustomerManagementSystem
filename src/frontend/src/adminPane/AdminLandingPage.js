
import AdminHeader from "./AdminHeader";
import {BrowserRouter as Router, Route,Switch} from "react-router-dom"
import Customers from "./Customers";
import CreditCustomer from "./CreditCustomer";
import Dashboard from "./Dashboard";
import Statistics from "./Statistics";
import Footer from "../headers/pages/Footer";
import AccountDetails from "./redux/containers/AccountDetails";

function AdminLandingPage() {

    return(

        <>
        <Router>
            <AdminHeader/>


            <Switch>

                <Route path="/adminPane/dashboard">
                   <Dashboard/>
                </Route>
                <Route path="/adminPane/Customers">
                   <Customers/>
                </Route>
                <Route path="/adminPane/creditCustomer">
                    <CreditCustomer/>
                </Route>
                <Route path="/adminPane/statistics">
                    <Statistics/>
                </Route>
                <Route path="/adminPane/:accountId">
                    <AccountDetails/>
                </Route>

            </Switch>

            {/*<Footer/>*/}
        </Router>

        </>
    )
}
export default AdminLandingPage