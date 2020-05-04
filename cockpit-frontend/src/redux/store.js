import { configureStore } from '@reduxjs/toolkit';
import { composeWithDevTools } from 'redux-devtools-extension';
import mvnMenuReducer from '../components/MvpMenu/mvpMenuSlice';
import ormReducer from './ormReducer';

export default configureStore(
  {
    reducer: {
      mvpMenu: mvnMenuReducer,
      orm: ormReducer,
    },
  },
  composeWithDevTools(),
);
