// import react, {useContext} from 'react'
import './../HomeHeader.css'
import cloud from "../../assets/cloud.png";
import React from "react";
import HomeHeader from "../HomeHeader";
import Footer from "./Footer";


function Products() {
    return(
        <>
            <HomeHeader/>
            <section className="showcase">
                <div className="container grid">
                    <div className="showcase-text">
                        <h1>Welcome!!</h1>
                        <p>get almost all products you need we are best
                            retailers and wholesellers in various products click the link below to view more
                        </p>

                    </div>
                    <div className="showcase-form ">
                        <img src={cloud} alt="good one" className="good-one"/>
                    </div>

                </div>
            </section>
            <Footer/>
        </>
    )
}

export default Products