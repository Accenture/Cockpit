import { createSlice } from '@reduxjs/toolkit';

export const HeaderSlice = createSlice({
  name: 'header',
  initialState: {
    open: false,
    edit: false,
  },
  reducers: {
    showScrumMasterForm: (state) => {
      state.open = true;
    },
    close: (state) => {
      state.open = false;
    },
    showEditMvpSMForm: (state) => {
      state.edit = true;
    },
    closeEditMvpSMForm: (state) => {
      state.edit = false;
    },
  },
});
export const {
  showScrumMasterForm,
  close,
  showEditMvpSMForm,
  closeEditMvpSMForm,
} = HeaderSlice.actions;

export const showSMFormState = (state) => state.header.open;
export const editSMFormState = (state) => state.header.edit;
export default HeaderSlice.reducer;
