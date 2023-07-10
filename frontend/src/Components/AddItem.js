import React from "react";
import {BsHouseAdd} from 'react-icons/bs'
import './AddItem.css'
import { useNavigate } from "react-router-dom";
function AddItem (props) {
    const navigate = useNavigate();
    return(
        <div className="addItem" >
             <BsHouseAdd size={50} onClick={() => navigate("/addroom")}/>
        </div>
    )
}

export default AddItem;