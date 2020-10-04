import {
  configureStore,
  combineReducers,
  getDefaultMiddleware,
} from '@reduxjs/toolkit';
import { composeWithDevTools } from 'redux-devtools-extension';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage/session';
import mvpMenuReducer from '../components/MvpMenu/mvpMenuSlice';
import mvpEntityFilterReducer from '../components/MvpEntityFilter/mvpEntityFilterSlice';
import ormReducer from './ormSlice';
import headerReducer from '../components/Header/HeaderSlice';
import smFormReducer from '../components/ScrumMasterForm/ScrumMasterFormSlice';
import editMvpFormReducer from '../components/InformationForm/InformationFormSlice';
import teamManagementFormReducer from '../components/TeamManagementForm/TeamManagementFormSlice';
import burnUpChartReducer from '../components/BurnUpChart/BurnUpChartSlice';
import homePageReducer from '../components/HomePage/HomePageSlice';

const persistConfig = {
  key: 'reduxStates',
  storage,
};

const reducers = combineReducers({
  mvpMenu: mvpMenuReducer,
  mvpEntityFilter: mvpEntityFilterReducer,
  header: headerReducer,
  ScrumMasterForm: smFormReducer,
  orm: ormReducer,
  InformationForm: editMvpFormReducer,
  TeamManagementForm: teamManagementFormReducer,
  BurnUpChart: burnUpChartReducer,
  home: homePageReducer,
});

const persistedReducer = persistReducer(persistConfig, reducers);

export default configureStore(
  {
    reducer: persistedReducer,
    middleware: getDefaultMiddleware({
      serializableCheck: false,
    }),
  },
  composeWithDevTools(),
);
