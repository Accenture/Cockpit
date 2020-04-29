import { configureStore } from '@reduxjs/toolkit';
import mvnMenuReducer from '../components/MvpMenu/mvpMenuSlice';

export default configureStore({
  reducer: {
    mvpMenu: mvnMenuReducer,
  },
});
