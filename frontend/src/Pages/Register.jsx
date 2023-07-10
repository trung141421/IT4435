import { useState } from "react"
import register from "../API/Register";
import "./Register.css";
import { useNavigate } from "react-router-dom";
export const Register = (props) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const handleSubmit = (e) => {
        e.preventDefault();
    }

    return (
        <div className="auth-form-container">
            <h2>Register</h2>
            <form className="register-form" onSubmit={handleSubmit}>
                <label htmlFor="username">Username</label>
                <input value={username} onChange={(e) => setUsername(e.target.value)} type="username" placeholder="your username"></input>
                <label htmlFor="password">Password</label>
                <input value={password} onChange={(e) => setPassword(e.target.value)} type="password" placeholder="your password"></input>
                <button onClick={() => navigate("/home")} type="submit">Register</button>
            </form>
            <button className="link-btn" onClick={() => navigate("/")}>Already have an account? Login here</button>
        </div>
    )
}