import API from '../common/utils/api';

const mvpUrl = 'mvp';
const jiraUrl = 'jira';
const burnUpChartUrl = 'burnUpChart';
const headers = {
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  },
};
function getAll() {
  try {
    return API.get(`${mvpUrl}/all`, headers);
  } catch (e) {
    console.log(`Error when getting all Mvps: ${e}`);
    return e;
  }
}

function createNewJiraProject(jira) {
  try {
    return API.post(`${jiraUrl}/create`, jira, headers);
  } catch (e) {
    console.log(`Error when creating new Jira Project: ${e}`);
    return e;
  }
}
function updateJiraProject(jira) {
  try {
    return API.put(`${jiraUrl}/update`, jira, headers);
  } catch (e) {
    console.log(`Error when creating new Jira Project: ${e}`);
    return e;
  }
}
function getBurnUpChartData(jiraProjectKey) {
  try {
    return API.get(`${burnUpChartUrl}/${jiraProjectKey}`, headers);
  } catch (e) {
    console.log(`Error when getting burn-up chart : ${e}`);
    return e;
  }
}
const MvpService = {
  getAll,
  createNewJiraProject,
  getBurnUpChartData,
  updateJiraProject,
};

export default MvpService;
