import { Model, attr, fk } from 'redux-orm';

export class Sprint extends Model {
  toString() {
    return `Sprint: ${this.name}`;
  }
}
Sprint.modelName = 'Sprint';

Sprint.fields = {
  id: attr(),
  sprintNumber: attr(),
  sprintEndDate: attr(),
  sprintStartDate: attr(),
  teamMood: attr(),
  teamMotivation: attr(),
  confidentTarget: attr(),
  technicalDebtKPI: attr(),
  mvp: fk('Mvp', 'sprints'),
};
export default Sprint;
