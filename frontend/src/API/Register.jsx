import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

const register = (username, password) => {
    return(
        instance.post('/auth/register', {
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

export default register;