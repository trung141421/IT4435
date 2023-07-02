import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

const getRoom = () => {
    return(
        instance.get('/room/getroom')
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          })
    )
}

export default getRoom;

