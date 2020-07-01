import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import MvpService from '../../services/service';

export const fetchBurnUpData = createAsyncThunk(
  'burnUpChart/fetchData',
  async (id) => {
    const chartData = await MvpService.getBurnUpChartData(id);
    return chartData.data;
  },
);
export const BurnUpChartSlice = createSlice({
  name: 'BurnUpChart',
  initialState: {
    chartData: [],
  },
  reducers: {},
  extraReducers: {
    [fetchBurnUpData.fulfilled]: (state, action) => {
      state.chartData = action.payload;
    },
  },
});
export const burnUpChartState = (state) => state.BurnUpChart.chartData;
export const { fetchData } = BurnUpChartSlice.actions;
export default BurnUpChartSlice.reducer;
