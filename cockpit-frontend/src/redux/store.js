import { configureStore } from '@reduxjs/toolkit';
import { composeWithDevTools } from 'redux-devtools-extension';
import mvnMenuReducer from '../components/MvpMenu/mvpMenuSlice';
import ormReducer from './ormSlice';

export default configureStore(
  {
    reducer: {
      mvpMenu: mvnMenuReducer,
      orm: ormReducer,
    },
  },
  composeWithDevTools(),
);
