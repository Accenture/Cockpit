import { createSlice } from '@reduxjs/toolkit';

export const mvpFilterSlice = createSlice({
  name: 'mvpEntityFilter',
  initialState: {
    value: 'ALL ENTITIES',
  },
  reducers: {
    selectEP: (state) => {
      state.value = 'EP';
    },
    selectRC: (state) => {
      state.value = 'RC';
    },
    selectMS: (state) => {
      state.value = 'MS';
    },
    selectGRP: (state) => {
      state.value = 'GRP';
    },
    selectTDF: (state) => {
      state.value = 'TDF';
    },
    selectAllEntities: (state) => {
      state.value = 'ALL ENTITIES';
    },
  },
});

export const {
  selectEP,
  selectRC,
  selectMS,
  selectGRP,
  selectTDF,
  selectAllEntities,
} = mvpFilterSlice.actions;

export const selectFilterState = (state) => state.mvpEntityFilter.value;
export default mvpFilterSlice.reducer;
