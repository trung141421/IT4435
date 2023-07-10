import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    responseType: 'json'
  });

  async function getRoom() {
    const response = await instance.get('/room/getroom')
    return response.data
}

export default getRoom;

