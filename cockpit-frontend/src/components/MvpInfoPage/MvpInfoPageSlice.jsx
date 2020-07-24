import { createSlice } from '@reduxjs/toolkit';

export const MvpInfoPageSlice = createSlice({
  name: 'MvpInfoPage',
  initialState: {
    selectedTab: 'overview',
  },
  reducers: {
    setSelectedTab: (state, action) => {
      state.selectedTab = action.payload;
    },
  },
});
export const { setSelectedTab } = MvpInfoPageSlice.actions;
export const selectedTabState = (state) => state.MvpInfoPage.selectedTab;
export default MvpInfoPageSlice.reducer;
