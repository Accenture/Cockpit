import axios from 'axios';

const env = process.env.REACT_APP_STAGE;
let backendUrl;

switch (env) {
  case 'local':
    backendUrl = 'http://localhost:8085';
    break;
  case 'dev':
    backendUrl = 'https://cockpit-backend-dev.azurewebsites.net';
    break;
  default:
    backendUrl = 'https://cockpit-backend-dev.azurewebsites.net';
}

export default axios.create({
  baseURL: `${backendUrl}/api/v1`,
  responseType: 'json',
});
