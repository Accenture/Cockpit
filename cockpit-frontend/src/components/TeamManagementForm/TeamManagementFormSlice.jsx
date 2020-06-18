import { createSlice } from '@reduxjs/toolkit';

export const TeamManagementFormSlice = createSlice({
  name: 'TeamManagementForm',
  initialState: {
    selectedTeam: {
      id: null,
      name: '',
      teamMembers: [],
      mvps: [],
    },
  },
  reducers: {
    setselectedTeam: (state, action) => {
      state.selectedTeam = action.payload;
    },
  },
});
export const { setselectedTeam } = TeamManagementFormSlice.actions;
export const selectedTeamState = (state) =>
  state.TeamManagementForm.selectedTeam;
export default TeamManagementFormSlice.reducer;
