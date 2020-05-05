import { Model, attr, fk } from 'redux-orm';
import { createSlice } from '@reduxjs/toolkit';

export class Sprint extends Model {
  static modelName = 'Sprint';

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
      mvp: fk('Mvp', 'sprints'),
    };
  }

  /* static reducer(action, sprint) {
    switch (action.type) {
      case 'SAVE_all_sprint': {
        action.payload.map((item) => sprint.create(item));
        break;
      }
      default:
        break;
    }
  } */
  static slice = createSlice({
    name: 'SprintSlice',
    initialState: undefined,
    reducers: {
      saveSprints(Sprint, action) {
        Sprint.create(action.payload);
      },
    },
  });

  toString() {
    return `Sprint: ${this.name}`;
  }
}
export default Sprint;
export const { saveSprints } = Sprint.slice.actions;
