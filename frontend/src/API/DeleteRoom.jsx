import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

const deleteRoom = (room_id) => {
    return(
        instance.post('/room/deleteroom', {
            room_id: room_id
          })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          })
    )
}

export default deleteRoom;

