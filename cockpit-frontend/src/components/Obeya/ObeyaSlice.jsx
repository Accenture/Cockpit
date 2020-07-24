import { createSlice } from '@reduxjs/toolkit';

export const ObeyaSlice = createSlice({
  name: 'Obeya',
  initialState: {
    mood: 0,
    motivation: 0,
    confidence: 0,
  },
  reducers: {
    setMood: (state, action) => {
      state.mood = action.payload;
    },
    setMotivation: (state, action) => {
      state.motivation = action.payload;
    },
    setConfidence: (state, action) => {
      state.confidence = action.payload;
    },
  },
});
export const { setMood, setMotivation, setConfidence } = ObeyaSlice.actions;
export const moodState = (state) => state.Obeya.mood;
export const motivationState = (state) => state.Obeya.motivation;
export const confidenceState = (state) => state.Obeya.confidence;
export default ObeyaSlice.reducer;
