import API from '../common/utils/api';

const mvpUrl = 'mvp';
const jiraUrl = 'jira';
const burnUpChartUrl = 'burnUpChart';
const teamUrl = 'team';
const sprintUrl = 'sprint';
const impedimentUrl = 'impediment';
const teamMemberUrl = 'teamMember';
const userUrl = 'user';
const technoUrl = 'technology';
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
    return API.delete(
      `${teamUrl}/${teamId}/deleteTeamMember/${teamMemberId}`,
      headers,
    );
  } catch (e) {
    console.log(`Error when deleting team member: ${e}`);
    return e;
  }
}
function getSprint(jiraId, sprintNumber) {
  try {
    return API.get(`${sprintUrl}/${jiraId}/${sprintNumber}`, headers);
  } catch (e) {
    console.log(`Error when getting Sprint: ${e}`);
    return e;
  }
}
function addObeya(obeya, jiraId, sprintNumber) {
  try {
    return API.put(
      `${sprintUrl}/${jiraId}/updateTeamHealth/${sprintNumber}`,
      obeya,
      headers,
    );
  } catch (e) {
    console.log(`Error when adding obeya: ${e}`);
    return e;
  }
}
function addImpediment(impediment, jiraId, sprintNumber) {
  try {
    return API.put(
      `${sprintUrl}/${jiraId}/addImpediment/${sprintNumber}`,
      impediment,
      headers,
    );
  } catch (e) {
    console.log(`Error when adding impediment: ${e}`);
    return e;
  }
}
function deleteImpediment(id) {
  try {
    return API.delete(`${impedimentUrl}/delete/${id}`, headers);
  } catch (e) {
    console.log(`Error when deleting impediment: ${e}`);
    return e;
  }
}
function deleteMvp(id) {
  try {
    return API.delete(`${jiraUrl}/delete/${id}`, headers);
  } catch (e) {
    console.log(`Error when deleting Mvp: ${e}`);
    return e;
  }
}
function updateImpediment(impediment, id) {
  try {
    return API.put(`${impedimentUrl}/update/${id}`, impediment, headers);
  } catch (e) {
    console.log(`Error when updating impediment: ${e}`);
    return e;
  }
}
function isUserScrumMaster() {
  try {
    return API.get(`${userUrl}/isScrumMaster`, headers);
  } catch (e) {
    console.log(`Error when verifying if user is scrum master: ${e}`);
    return e;
  }
}
function unassignTeamMember(teamId, teamMemberId) {
  try {
    return API.put(
      `${teamUrl}/${teamId}/unassignTeamMember/${teamMemberId}`,
      headers,
    );
  } catch (e) {
    console.log(`Error when unassigning team member: ${e}`);
    return e;
  }
}
function getMembers() {
  try {
    return API.get(`${teamMemberUrl}/all`, headers);
  } catch (e) {
    console.log(`Error when getting members : ${e}`);
    return e;
  }
}
function assignTeamMember(teamId, teamMemberId) {
  try {
    return API.put(
      `${teamUrl}/${teamId}/assignTeamMember/${teamMemberId}`,
      headers,
    );
  } catch (e) {
    console.log(`Error when assigning team member: ${e}`);
    return e;
  }
}
function deleteTeam(id) {
  try {
    return API.delete(`${teamUrl}/delete/${id}`, headers);
  } catch (e) {
    console.log(`Error when deleting team: ${e}`);
    return e;
  }
}
function addTechnology(technology, id) {
  try {
    return API.post(`${technoUrl}/${id}/create`, technology, headers);
  } catch (e) {
    console.log(`Error when creating technology: ${e}`);
    return e;
  }
}
function getAllTechnologies() {
  try {
    return API.get(`${technoUrl}/all`, headers);
  } catch (e) {
    console.log(`Error when getting all technologies: ${e}`);
    return e;
  }
}
function assignTechnology(technologyId, mvpId) {
  try {
    return API.put(`${technoUrl}/${mvpId}/assign/${technologyId}`, headers);
  } catch (e) {
    console.log(`Error when assigning technology: ${e}`);
    return e;
  }
}
function unassignTechnology(technologyId, mvpId) {
  try {
    return API.put(`${technoUrl}/${mvpId}/unassign/${technologyId}`, headers);
  } catch (e) {
    console.log(`Error when unassigning technology: ${e}`);
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
  getSprint,
  addObeya,
  addImpediment,
  deleteImpediment,
  deleteMvp,
  updateImpediment,
  getMembers,
  unassignTeamMember,
  isUserScrumMaster,
  assignTeamMember,
  deleteTeam,
  addTechnology,
  getAllTechnologies,
  assignTechnology,
  unassignTechnology,
};

export default MvpService;
