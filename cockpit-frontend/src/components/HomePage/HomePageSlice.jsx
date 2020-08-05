import { createSlice } from '@reduxjs/toolkit';

export const HomePageSlice = createSlice({
  name: 'home',
  initialState: {
    isScrumMaster: null,
  },
  reducers: {
    setUserIsScrumMaster: (state, action) => {
      state.isScrumMaster = action.payload;
    },
  },
});
export const { setUserIsScrumMaster } = HomePageSlice.actions;

export const isScrumMasterState = (state) => state.home.isScrumMaster;
export default HomePageSlice.reducer;
