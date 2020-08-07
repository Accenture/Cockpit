import { createSlice } from '@reduxjs/toolkit';

export const InformationFormSlice = createSlice({
  name: 'InformationForm',
  initialState: {
    name: '',
    pitch: '',
    cycle: 1,
    scopeCommitment: 0,
    sprintNumber: 0,
    status: '',
    entity: '',
    urlMvpAvatar: '',
    mvpStartDate: '',
    mvpEndDate: '',
  },
  reducers: {
    setName: (state, action) => {
      state.name = action.payload;
    },
    setPitch: (state, action) => {
      state.pitch = action.payload;
    },
    setEntity: (state, action) => {
      state.entity = action.payload;
    },
    setCycle: (state, action) => {
      state.cycle = action.payload;
    },
    setScopeCommitment: (state, action) => {
      state.scopeCommitment = action.payload;
    },
    setSprintNumber: (state, action) => {
      state.sprintNumber = action.payload;
    },
    setStatus: (state, action) => {
      state.status = action.payload;
    },
    setImageUrl: (state, action) => {
      state.urlMvpAvatar = action.payload;
    },
    setMvpStartDate: (state, action) => {
      state.mvpStartDate = action.payload;
    },
    setMvpEndDate: (state, action) => {
      state.mvpEndDate = action.payload;
    },
  },
});
export const {
  setName,
  setPitch,
  setEntity,
  setCycle,
  setScopeCommitment,
  setSprintNumber,
  setStatus,
  setImageUrl,
  setMvpStartDate,
  setMvpEndDate,
} = InformationFormSlice.actions;

export const nameState = (state) => state.InformationForm.name;
export const pitchState = (state) => state.InformationForm.pitch;
export const entityState = (state) => state.InformationForm.entity;
export const cycleState = (state) => state.InformationForm.cycle;
export const scopeCommitmentState = (state) =>
  state.InformationForm.scopeCommitment;
export const sprintNumberState = (state) => state.InformationForm.sprintNumber;
export const statusState = (state) => state.InformationForm.status;
export const imageUrlState = (state) => state.InformationForm.urlMvpAvatar;
export const mvpStartDateState = (state) => state.InformationForm.mvpStartDate;
export const mvpEndDateState = (state) => state.InformationForm.mvpEndDate;
export default InformationFormSlice.reducer;
