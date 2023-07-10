import { useState } from "react"
import logIn from "../API/Login";
import "./Login.css";
import { useNavigate } from "react-router-dom";


export const Login = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const handleSubmit = (e) => {
        e.preventDefault();
    }

    function sayHello() {
        alert('Hello!');
    }
    console.log("hello")
    return (  
        <div className="auth-form-container">
            <h2>Login</h2>
            <form className="login-form" onSubmit={handleSubmit}>
                <label htmlFor="username">Username</label>
                <input value={username} onChange={(e) => setUsername(e.target.value)} type="username" placeholder="your username"></input>
                <label htmlFor="password">Password</label>
                <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" placeholder="your password"></input>
                <button onClick={() => navigate("/home")} type="submit">Log In</button>
            </form>
            <button className="link-btn" onClick={() => navigate("/register")}>Don't have an account? Register here</button>
        </div>
    )
}