import { Model, attr, ORM } from 'redux-orm';

export class Sprint extends Model {
  static get fields() {
    return {
      id: attr(),
      sprintNumber: attr(),
      sprintEndDate: attr(),
      sprintStartDate: attr(),
      teamMood: attr(),
      teamMotivation: attr(),
      confidentTarget: attr(),
      technicalDebtKPI: attr(),
    };
  }

  static reducer(action, sprint) {
    switch (action.type) {
      case 'SAVE_all_sprint': {
        action.payload.map((item) => sprint.create(item));
        break;
      }
      default:
        break;
    }
  }
}
Sprint.modelName = 'Sprint';

export const orm = new ORM();
orm.register(Sprint);
