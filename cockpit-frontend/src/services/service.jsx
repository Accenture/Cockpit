import API from '../common/utils/api';

function getAll() {
  return API.get('/all');
}
function getLightMvp() {
  return API.get('/light/all');
}
function getOne(id) {
  return API.get(`/${id}`);
}
function removeOne(id) {
  return API.put(`/removeMvp/${id}`);
}
function updateOne(id, mvp) {
  return API.put(`/${id}`, mvp);
}
const MvpService = {
  getAll,
  getLightMvp,
  getOne,
  removeOne,
  updateOne,
};

export default MvpService;
