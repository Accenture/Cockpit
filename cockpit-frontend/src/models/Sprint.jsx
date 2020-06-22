/*
import { Model, attr, fk } from 'redux-orm';

export class Sprint extends Model {
  toString() {
    return `Sprint: ${this.name}`;
  }
}
Sprint.modelName = 'Sprint';

Sprint.fields = {
  id: attr(),
  sprintStartDate: attr(),
  sprintEndDate: attr(),
  teamMotivation: attr(),
  teamMood: attr(),
  teamConfidence: attr(),
  totalNbUs: attr(),
  sprintNumber: attr(),
  idJira: fk({
    to: 'Jira',
    as: 'jira',
    relatedName: 'sprints',
  }),
};
export default Sprint;
*/
