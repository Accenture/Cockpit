import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import MvpService from '../services/apiService';
import orm from './orm';

const withSession = (reducer) => (state, action) => {
  const session = orm.session(state);
  reducer(session, action);
  return session.state;
};

export const fetchAllMvps = createAsyncThunk('mvps/fetchAllMvps', async () => {
  const allMvps = await MvpService.getAllMvp();
  return allMvps.data;
});
export const getOneMvp = createAsyncThunk('mvps/getOneMvp', async (id) => {
  const mvp = await MvpService.getOneMvp(id);
  return mvp.data;
});

const ormSlice = createSlice({
  name: 'mvps',
  initialState: orm.getEmptyState(),
  reducers: {
    // Put sync reducers here
  },
  extraReducers: {
    // Put async reducers here
    [fetchAllMvps.fulfilled]: withSession((session, action) => {
      // Add mvp to the state array
      action.payload.forEach((mvp) => {
        if (session.Mvp.withId(mvp.id) == null) {
          session.Mvp.create(mvp);
        } else {
          session.Mvp.withId(mvp.id).update(mvp);
        }
      });
    }),
    [getOneMvp.fulfilled]: withSession((session, action) => {
      // Add mvp to the state array
      const mvp = action.payload;
      if (session.Mvp.withId(mvp.id) == null) {
        session.Mvp.create(mvp);
      } else {
        session.Mvp.withId(mvp.id).update(mvp);
      }
    }),
  },
});
export const { fetchMvps } = ormSlice.actions;
export default ormSlice.reducer;
