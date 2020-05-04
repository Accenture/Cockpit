import { Model, attr, fk, many } from 'redux-orm';
import { createSlice } from '@reduxjs/toolkit';
import MvpService from '../services/service';

export class Mvp extends Model {
  static modelName = 'Mvp';

  static get fields() {
    return {
      id: attr(),
      name: attr(),
      pitch: attr(),
      status: attr(),
      entity: attr(),
      currentSprint: attr(),
      nbSprint: attr(),
      mvpEndDate: attr(),
      mvpStartDate: attr(),
      mvpAvatarUrl: attr(),
      location: attr(),
      nbUserStories: attr(),
      bugsCount: attr(),
      allBugsCount: attr(),
      timeToFix: attr(),
      timeToDetect: attr(),
      technicalDebt: attr(),
      sharedMVPId: attr(),
      iterationNumber: attr(),
      teamId: fk({
        to: 'Team',
        as: 'team',
        relatedName: 'mvps',
      }),
      sprints: many('Sprint'),
      technologies: many('Technology'),
    };
  }

  /* static reducer(action, mvp) {
    switch (action.type) {
      case 'SAVE_MVPS': {
        action.payload.map((item) => mvp.create(item));
        break;
      }
      default:
        break;
    }
  } */

  static slice = createSlice({
    name: 'MvpSlice',
    initialState: undefined,
    reducers: {
      async fetchMvps(Mvp, action) {
        const response = await MvpService.getAll();
        const data = await response.data;
        action.payload = data;
        Mvp.create(action.payload);
      },
    },
  });

  toString() {
    return `Mvp: ${this.name}`;
  }
}
export default Mvp;
export const { fetchMvps } = Mvp.slice.actions;
