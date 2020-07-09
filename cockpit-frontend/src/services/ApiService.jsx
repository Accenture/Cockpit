import API from '../common/utils/api';

const mvpUrl = 'mvp';
const jiraUrl = 'jira';
const burnUpChartUrl = 'burnUpChart';
const teamUrl = 'team';
const headers = {
  headers: {
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
  },
};
function getAllMvp() {
  try {
    return API.get(`${mvpUrl}/all`, headers);
  } catch (e) {
    console.log(`Error when getting all Mvps: ${e}`);
    return e;
  }
}
function getOneMvp(id) {
  try {
    return API.get(`${mvpUrl}/${id}`, headers);
  } catch (e) {
    console.log(`Error when getting Mvp: ${e}`);
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
function getTeams() {
  try {
    return API.get(`${teamUrl}/all`, headers);
  } catch (e) {
    console.log(`Error when getting teams : ${e}`);
    return e;
  }
}
function createNewTeam(team, mvpId) {
  try {
    return API.post(`${teamUrl}/create/${mvpId}`, team, headers);
  } catch (e) {
    console.log(`Error when creating new team: ${e}`);
    return e;
  }
}
function assignTeam(id, teamId) {
  try {
    return API.put(`${mvpUrl}/${id}/assignTeam/${teamId}`, headers);
  } catch (e) {
    console.log(`Error when assign team: ${e}`);
    return e;
  }
}
function unassignTeam(id) {
  try {
    return API.put(`${mvpUrl}/unassignTeam/${id}`, headers);
  } catch (e) {
    console.log(`Error when unassign team: ${e}`);
    return e;
  }
}
function createNewTeamMember(teamMember, teamId) {
  try {
    return API.put(`${teamUrl}/addTeamMember/${teamId}`, teamMember, headers);
  } catch (e) {
    console.log(`Error when creating new team member: ${e}`);
    return e;
  }
}
function deleteTeamMember(teamId, teamMemberId) {
  try {
    return API.put(
      `${teamUrl}/${teamId}/deleteTeamMember/${teamMemberId}`,
      headers,
    );
  } catch (e) {
    console.log(`Error when deleting team member: ${e}`);
    return e;
  }
}
const MvpService = {
  getAllMvp,
  createNewJiraProject,
  getBurnUpChartData,
  updateJiraProject,
  getTeams,
  createNewTeam,
  assignTeam,
  unassignTeam,
  getOneMvp,
  createNewTeamMember,
  deleteTeamMember,
};

export default MvpService;
