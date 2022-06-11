
import './Footer.css'
import {useContext} from "react";
import AuthContext from "../../context/AuthContext";

function Login() {
  let {loginUser} = useContext(AuthContext)
    return(
        <>
            <div className="show-form ">
                <h1>Login</h1>
            <form onSubmit={loginUser}>
                <div className="text-field">
                 <input type="text" name="username" placeholder="Username"/>
                </div>

                <div className="text-field">
                    <input type="password" name="password" placeholder="password"/>
                </div>
                <h2>forgot password?</h2>
                <input type="submit" value="SigIn"/>
            </form>


            </div>
        </>
    )
}
export default Login