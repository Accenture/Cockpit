import { Model, attr, many } from 'redux-orm';
import { createSlice } from '@reduxjs/toolkit';

export class Team extends Model {
  static modelName = 'Team';

  static get fields() {
    return {
      id: attr(),
      name: attr(),
      teamMembers: many('TeamMember'),
    };
  }

  /* static reducer(action, team) {
    switch (action.type) {
      case 'CREATE_TEAM': {
        team.create(action.payload);
        break;
      }
      default:
        break;
    }
  } */
  static slice = createSlice({
    name: 'TeamSlice',
    initialState: undefined,
    reducers: {
      saveTeam(Team, action) {
        Team.create(action.payload);
      },
    },
  });

  toString() {
    return `Team: ${this.name}`;
  }
}
export default Team;
export const { saveTeam } = Team.slice.actions;
