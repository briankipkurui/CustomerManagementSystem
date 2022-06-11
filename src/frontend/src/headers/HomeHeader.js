// import react from 'react'
import {Link} from "react-router-dom";
import React from "react";
import './HomeHeader.css';


  function HomeHeader() {
  return(
      <>
        <div className="navbar">
          <div className="container  flex ">
            <h1 className="logo">Amani</h1>

           <nav>
             <ul>
               <li>

                 <Link to='/' className="nav-menu">
                  HOME
                 </Link>

               </li>
               <li>
                 <Link to='/products' className="nav-menu">
                   PRODUCTS
                 </Link>
               </li>
               <li>
                 <Link to='/login' className="nav-menu">
                   LOGIN
                 </Link>
               </li>
             </ul>
           </nav>

          </div>

        </div>


      </>
  )
}
export default HomeHeader;