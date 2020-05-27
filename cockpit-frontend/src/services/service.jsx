import API from '../common/utils/api';

const mvpUrl = 'mvp';
const burnUpChartUrl = 'burnup';
function getAll() {
  try {
    return API.get(`${mvpUrl}/all`);
  } catch (e) {
    console.log(`get all MVPS API call Error: ${e}`);
    return e;
  }
}

function createMvp(mvp) {
  try {
    return API.post(`${mvpUrl}/createMvp`, mvp);
  } catch (e) {
    console.log(`remove MVP API call Error: ${e}`);
    return e;
  }
}
function getBurnUpChartData(jiraProjectKey) {
  try {
    return API.get(`${burnUpChartUrl}/${jiraProjectKey}`);
  } catch (e) {
    console.log(`remove MVP API call Error: ${e}`);
    return e;
  }
}
const MvpService = {
  getAll,
  createMvp,
  getBurnUpChartData,
};

export default MvpService;
