import API from '../common/utils/api';

function getAll() {
  try {
    return API.get('/all');
  } catch (e) {
    console.log(`get all MVPS API call Error: ${e}`);
    return e;
  }
}
function getLightMvp() {
  try {
    return API.get('/light/all');
  } catch (e) {
    console.log(`get all light MVPS API call Error: ${e}`);
    return e;
  }
}
function getOne(id) {
  try {
    return API.get(`/${id}`);
  } catch (e) {
    console.log(`get all MVP by ID API call Error: ${e}`);
    return e;
  }
}
function removeOne(id) {
  try {
    return API.put(`/removeMvp/${id}`);
  } catch (e) {
    console.log(`remove MVP API call Error: ${e}`);
    return e;
  }
}
function updateOne(id, mvp) {
  try {
    return API.put(`/${id}`, mvp);
  } catch (e) {
    console.log(`remove MVP API call Error: ${e}`);
    return e;
  }
}
const MvpService = {
  getAll,
  getLightMvp,
  getOne,
  removeOne,
  updateOne,
};

export default MvpService;
