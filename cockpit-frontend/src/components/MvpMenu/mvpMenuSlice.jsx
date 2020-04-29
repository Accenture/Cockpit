import { createSlice } from '@reduxjs/toolkit';

export const mvpMenuSlice = createSlice({
  name: 'mvpMenu',
  initialState: {
    value: 'In Progress',
  },
  reducers: {
    selectCandidates: (state) => {
      state.value = 'Candidates';
    },
    selectInProgress: (state) => {
      state.value = 'In Progress';
    },
    selectTransferred: (state) => {
      state.value = 'Transferred';
    },
  },
});

export const {
  selectCandidates,
  selectInProgress,
  selectTransferred,
} = mvpMenuSlice.actions;

export const selectMvpState = (state) => state.mvpMenu.value;
export default mvpMenuSlice.reducer;
