import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

const editRoom = (id, name, address, description, price, renter_name) => {
    return(
        instance.post('/room/editroom', {
            room_id: id,
            name: name,
            address: address,
            description: description,
            price: price,
            renter_name: renter_name
          })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          })
    )
}

export default editRoom;

