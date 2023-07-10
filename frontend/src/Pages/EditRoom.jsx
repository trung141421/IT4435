import React, { useState } from "react";
import './EditRoom.css'
import { useNavigate } from "react-router-dom";
import { useLocation } from 'react-router-dom'
import editRoom from "../API/EditRoom";
function EditRoom (props) {
    console.log(props);
    const navigate = useNavigate();
    const location = useLocation();
    console.log('location', location.state.props.props)
    const [roomName, setRoomName] = useState([location.state.props.props.name]);
    const [address, setAddress] = useState([location.state.props.props.address]);
    const [description, setDescription] = useState([location.state.props.props.description]);
    const [price, setPrice] = useState([location.state.props.props.price]);
    const [renterName, setRenterName] = useState([location.state.props.props.renter_name]);
    const [id, setId] = useState([location.state.props.props.id]);
    const handleSubmit = (e) => {
        e.preventDefault();
    }
    return(
        <div className="editRoom">
             <div className="container">  
  <form id="contact" action="" method="post" onSubmit={handleSubmit}>
    <h3>Edit Room Form</h3>
    <fieldset>
      <input value={roomName} onChange={(e) => setRoomName(e.target.value)} placeholder="Room's name" type="text" tabIndex="1" required autoFocus/>
    </fieldset>
    <fieldset>
      <input value={address} onChange={(e) => setAddress(e.target.value)} placeholder="Address" type="text" tabIndex="2" required/>
    </fieldset>
    <fieldset>
      <textarea value={description} onChange={(e) => setDescription(e.target.value)} placeholder="Description" tabIndex="3" required></textarea>
    </fieldset>
    <fieldset>
      <input value={price} onChange={(e) => setPrice(e.target.value)} placeholder="Price" type="text" tabIndex="4" required/>
    </fieldset>
    <fieldset>
      <input value={renterName} onChange={(e) => setRenterName(e.target.value)} placeholder="Renter's name" type="text" tabIndex="5" required/>
    </fieldset>
    <fieldset>
      <button name="submit" type="submit" id="contact-submit" data-submit="...Sending" onClick={event => {editRoom(id,roomName, address, description, price, renterName);navigate("/home")}}>Submit</button>
    </fieldset>
  </form>
</div>
        </div>
    )
}

export default EditRoom;