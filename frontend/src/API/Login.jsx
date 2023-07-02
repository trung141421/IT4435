import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

const logIn = (username, password) => {
    return(
        instance.post('/auth/login', {
            username: username,
            password: password
          })
          .then(function (response) {
            console.log(response);
          })
          .catch(function (error) {
            console.log(error);
          })
    )
}

export default logIn;

