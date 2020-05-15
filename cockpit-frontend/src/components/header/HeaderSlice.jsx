import { createSlice } from '@reduxjs/toolkit';

export const HeaderSlice = createSlice({
  name: 'header',
  initialState: {
    open: false,
  },
  reducers: {
    showScrumMasterForm: (state) => {
      state.open = true;
    },
    close: (state) => {
      state.open = false;
    },
  },
});
export const { showScrumMasterForm, close } = HeaderSlice.actions;

export const showSMFormState = (state) => state.header.open;
export default HeaderSlice.reducer;
