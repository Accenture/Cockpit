import { Model, attr } from 'redux-orm';
import { createSlice } from '@reduxjs/toolkit';

export class TeamMember extends Model {
  static modelName = 'TeamMember';

  static get fields() {
    return {
      idMember: attr(),
      lastName: attr(),
      firstName: attr(),
      emailId: attr(),
      avatarUrl: attr(),
      idRole: attr(),
    };
  }

  static slice = createSlice({
    name: 'TeamMemberSlice',
    initialState: undefined,
    reducers: {
      saveTeamMembers(TeamMember, action) {
        TeamMember.create(action.payload);
      },
    },
  });

  toString() {
    return `TeamMember: ${this.name}`;
  }
}
export default TeamMember;
export const { saveTeamMembers } = TeamMember.slice.actions;
