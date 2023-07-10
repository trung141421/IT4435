import React ,{ useState }from "react";
import './AddRoom.css'
import { useNavigate } from "react-router-dom";
import createRoom from "../API/CreateRoom";
function AddRoom (props) {
    const navigate = useNavigate();
    const [roomName, setRoomName] = useState([props.name]);
    const [address, setAddress] = useState([props.address]);
    const [description, setDescription] = useState([props.description]);
    const [price, setPrice] = useState([props.price]);
    const handleSubmit = (e) => {
        e.preventDefault();
    }
    return(
        <div className="addRoom">
             <div className="container">  
  <form id="contact" action="" method="post" onSubmit={handleSubmit}>
    <h3>Add Room Form</h3>
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
      <button name="submit" type="submit" id="contact-submit" data-submit="...Sending" onClick={event => {createRoom(roomName, address, description, price);navigate("/home")}}>Submit</button>
    </fieldset>
  </form>
</div>
        </div>
    )
}

export default AddRoom;