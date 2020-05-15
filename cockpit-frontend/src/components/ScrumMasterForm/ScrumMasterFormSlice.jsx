import { createSlice } from '@reduxjs/toolkit';

export const ScrumMasterFormSlice = createSlice({
  name: 'ScrumMasterForm',
  initialState: {
    mvp: null,
    jiraId: null,
    entity: null,
    cycle: null,
    imageUrl: null,
    formIsValid: false,
  },
  reducers: {
    setMvp: (state, action) => {
      state.mvp = action.payload;
    },
    setJiraId: (state, action) => {
      state.jiraId = action.payload;
    },
    setEntity: (state, action) => {
      state.entity = action.payload;
    },
    setCycle: (state, action) => {
      state.cycle = action.payload;
    },
    setImageUrl: (state, action) => {
      state.imageUrl = action.payload;
    },
    setFormIsValid: (state) => {
      if (
        state.mvp != null &&
        state.entity != null &&
        state.cycle != null &&
        state.jiraId != null &&
        state.imageUrl != null
      )
        state.formIsValid = true;
    },
    setAlltoNull: (state) => {
      state.mvp = null;
      state.entity = null;
      state.cycle = null;
      state.jiraId = null;
      state.imageUrl = null;
      state.formIsValid = false;
    },
  },
});
export const {
  setMvp,
  setJiraId,
  setEntity,
  setCycle,
  setFormIsValid,
  setImageUrl,
  setAlltoNull,
} = ScrumMasterFormSlice.actions;

export const mvpState = (state) => state.ScrumMasterForm.mvp;
export const jiraIdState = (state) => state.ScrumMasterForm.jiraId;
export const entityState = (state) => state.ScrumMasterForm.entity;
export const cycleState = (state) => state.ScrumMasterForm.cycle;
export const imageUrlState = (state) => state.ScrumMasterForm.imageUrl;
export const SMFormState = (state) => state.ScrumMasterForm.formIsValid;
export default ScrumMasterFormSlice.reducer;
