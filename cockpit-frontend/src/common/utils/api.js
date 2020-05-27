import axios from 'axios';

export default axios.create({
  baseURL: 'http://localhost:8085/api/1.0/',
  responseType: 'json',
});
