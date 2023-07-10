import React from "react";
import './RoomItem.css';
import { useNavigate } from "react-router-dom";
function RoomItem (props) {
    const navigate = useNavigate();
    const navigateToEditRoom = () => {
        navigate("/editroom", {
            state: {
                props
            },
        });
      };
    return(
        <div className="roomItem" onClick={navigateToEditRoom}>
             <div></div>
             <h2 >{props.props.name}</h2>
        </div>
    )
}

export default RoomItem;