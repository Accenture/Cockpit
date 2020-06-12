import { Model, attr, many } from 'redux-orm';

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
  technologies: many('Technology'),
};

export default Jira;
