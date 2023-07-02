import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

const createRoom = (name, address, description, price) => {
    return(
        instance.post('/room/createroom', {
            name: name,
            address: address,
            description: description,
            price: price
          })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          })
    )
}

export default createRoom;

