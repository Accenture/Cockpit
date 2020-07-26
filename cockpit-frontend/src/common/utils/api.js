import axios from 'axios';

const env = process.env.REACT_APP_STAGE;
let backendUrl;

switch (env) {
  case 'local':
    backendUrl = 'http://localhost:8085';
    break;
  case 'dev':
    backendUrl = 'https://azwbdcokp02.azurewebsites.net';
    break;
  case 'qa':
    backendUrl = 'https://azwbqcokp02.azurewebsites.net';
    break;
  default:
    backendUrl = 'http://localhost:8085';
}
const API = axios.create({
  baseURL: `${backendUrl}/api/v1`,
  responseType: 'json',
});

API.defaults.headers.common.Authorization = `Bearer ${window.sessionStorage.getItem(
  'access_token',
)}`;

export default API;
