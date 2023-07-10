import React from "react";
import {Link} from "react-router-dom";
import "./Navbar.css"
function Navbar(props){
    return (
        <div className="navbar">
            <div className="leftSide">
                
            </div>
            <div className="rightSide">
            <button className="link-btn" onClick={() => props.onFormSwitch('login')}>Log Out</button>
            </div>
        </div>
    )
}

export default Navbar;