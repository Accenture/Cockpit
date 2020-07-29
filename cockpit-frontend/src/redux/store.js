import {
  configureStore,
  combineReducers,
  getDefaultMiddleware,
} from '@reduxjs/toolkit';
import { composeWithDevTools } from 'redux-devtools-extension';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage/session';
import mvnMenuReducer from '../components/MvpMenu/mvpMenuSlice';
import ormReducer from './ormSlice';
import headerReducer from '../components/Header/HeaderSlice';
import smFormReducer from '../components/ScrumMasterForm/ScrumMasterFormSlice';
import editMvpFormReducer from '../components/InformationForm/InformationFormSlice';
import teamManagementFormReducer from '../components/TeamManagementForm/TeamManagementFormSlice';
import burnUpChartReducer from '../components/BurnUpChart/BurnUpChartSlice';
// import obeyaReducer from '../components/Obeya/ObeyaSlice';
import mvpInfoReducer from '../components/MvpInfoPage/MvpInfoPageSlice';

const persistConfig = {
  key: 'reduxStates',
  storage,
};

const reducers = combineReducers({
  mvpMenu: mvnMenuReducer,
  header: headerReducer,
  ScrumMasterForm: smFormReducer,
  orm: ormReducer,
  InformationForm: editMvpFormReducer,
  TeamManagementForm: teamManagementFormReducer,
  BurnUpChart: burnUpChartReducer,
  // Obeya: obeyaReducer,
  MvpInfoPage: mvpInfoReducer,
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
