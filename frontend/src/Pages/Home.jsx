import React, { useEffect, useState } from "react";
import RoomItem from "../Components/RoomItem";
import getRoom from "../API/GetRoom";
import "./Home.css";
import AddItem from "../Components/AddItem";
export const Home = (props) => {
    const [roomlist, setRoomlist] = useState([]);
    useEffect(() => {
        getRoom().then((res) => {
            setRoomlist(res.data);
        })
        .catch(function (error) {
            console.log(error);
          })
    },[roomlist])
    return (
        <div>
            
        <div className="room">
            <h1 className="title">Room List</h1>
            <AddItem/>
            <div className="roomlist">
                {
                    roomlist.map(function(data){
                        return(
                            <RoomItem key={data.id} props={data}/>
                        )
                    })
                }
                
            </div>
        
        </div>
        </div>
    );
}