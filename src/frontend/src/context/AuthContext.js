import {createContext, useState} from 'react'
import fetch from "unfetch";
import jwt_decode from "jwt-decode"
import {useHistory} from "react-router-dom"

const AuthContext = createContext()

export  default  AuthContext;

export const  AuthProvider = ({children}) =>{
    let history = useHistory()
    let [authTokens, setAuthTokens] =
        useState(() => localStorage.getItem('authTokens') ? JSON.parse(localStorage.getItem('authTokens')) : (null))
    let [user, setUser] =
        useState(() => localStorage.getItem('authTokens') ? jwt_decode(localStorage.getItem('authTokens')) : (null))


    let loginUser  = async (e) =>{
        e.preventDefault()
        let response = await fetch("api/v1/registration/login",{
            method:'POST',
            headers:{
                'content-Type': 'application/json'
            },
            body:JSON.stringify({'username':e.target.username.value,'password':e.target.password.value})
        })
        let data = await  response.json()
        if(response.status === 200){
            setAuthTokens(data)
            setUser(jwt_decode(data.access_token))



            localStorage.setItem('authTokens', JSON.stringify(data))
        }else {
            alert('something went wrong')
        }
        let jwtDecode = jwt_decode(data.access_token)
        console.log("data",jwtDecode)
        let isUser = (jwtDecode.authorities.length)>1
        console.log("isUser",isUser)
        if (!isUser){
            history.push('/userPane')

        }else {
            history.push('/adminPane/Customers')
        }

    }

    // let logoutUser = () => {
    //     setAuthTokens(null)
    //     setUser(null)
    //     localStorage.removeItem('authTokens')
    //     history.push("/login")
    // }

    let logoutUser =() =>{
        setAuthTokens(null)
        setUser(null)
        localStorage.removeItem('authTokens')
        history.push('/')
    }


    let contextData ={
        user:user,
        loginUser:loginUser,
        authTokens:authTokens,
        logoutUser:logoutUser

    }
    return(
        <AuthContext.Provider value={contextData}>
            {children}
        </AuthContext.Provider>
    )
}