import API from '../common/utils/api';

function getAll() {
  try {
    return API.get('/all');
  } catch (e) {
    console.log(`get all MVPS API call Error: ${e}`);
    return e;
  }
}

function createMvp(mvp) {
  try {
    return API.post(`/createMvp`, mvp);
  } catch (e) {
    console.log(`remove MVP API call Error: ${e}`);
    return e;
  }
}
const MvpService = {
  getAll,
  createMvp,
};

export default MvpService;
