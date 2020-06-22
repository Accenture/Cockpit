/*
import { Model, attr, oneToOne } from 'redux-orm';

export class Jira extends Model {
  toString() {
    return `Jira: ${this.name}`;
  }
}
Jira.modelName = 'Jira';

Jira.fields = {
  id: attr(),
  jiraProjectKey: attr(),
  currentSprint: attr(),
  jiraProjectId: attr(),
  mvpStartDate: attr(),
  mvpEndDate: attr(),
  idMvp: oneToOne({
    to: 'Mvp',
    as: 'mvp',
    relatedName: 'jira',
  }),
};

export default Jira;
*/
