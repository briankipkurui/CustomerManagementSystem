import {Link} from "react-router-dom";
import React, {useContext, useState} from "react";
import './AdminHeader.css'
import AuthContext from "../context/AuthContext";
import {FaBars, FaTimes} from "react-icons/all";



function AdminHeader() {
    let {logoutUser} = useContext(AuthContext)
    const [click, setClick] = useState(false)

    const handleClick =()=> setClick(!click)

    return(
        <>
            <div className="adminNavbar">
                <div className="container  flex ">
                    <h1>Amani</h1>

                    <nav>

                        <ul>
                            <li>
                                <Link to='/adminPane/dashboard' className="nav-menu-dashboard">
                                    dashboard
                                </Link>
                            </li>
                            <li>
                                <Link to='/adminPane/Customers' className="nav-menu">
                                    customer
                                </Link>
                            </li>
                            <li>
                                <Link to='/adminPane/creditCustomer' className="nav-menu">
                                    credit-customer
                                </Link>
                            </li>
                            <li>
                                <Link to='/adminPane/statistics' className="nav-menu">
                                    statistics
                                </Link>
                            </li>

                            <li className="nav-menu-last">
                                <Link to='#' className="nav-menu"  >
                                    change-pwd
                                </Link>
                            </li>

                            <li className="nav-menu-last">
                                <Link to='/' className="nav-menu" onClick={logoutUser} >
                                    log-out
                                </Link>


                            </li>



                        </ul>

                    </nav>
                    <div className="menu-icon text-center" onClick={handleClick}>
                        {click ? <FaTimes/> : <FaBars/>}

                    </div>


                </div>

            </div>


        </>
    )
}
export default AdminHeader