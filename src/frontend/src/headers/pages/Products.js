// import react from 'react'

import HomeHeader from "../HomeHeader";
import '././Products.css'

import image from './../../assets/1.jpg'
import Footer from "./Footer";
function Products() {
    return(
        <>
            <HomeHeader/>
            <section className="hardware-tools">
                <h2 className=" container text-center  py-1  ">
                  hardware tools
                </h2>
                <div className="flex-items container flex ">
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>
                    <div className=" card">
                        <img src={image} alt=""/>
                        <h4>chiesel</h4>
                    </div>

                </div>

            </section>
            <Footer/>

        </>
    )
}

export default Products