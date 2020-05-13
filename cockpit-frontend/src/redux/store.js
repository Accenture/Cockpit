import {
  configureStore,
  combineReducers,
  getDefaultMiddleware,
} from '@reduxjs/toolkit';
import { composeWithDevTools } from 'redux-devtools-extension';
import { persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // defaults to localStorage for web
import mvnMenuReducer from '../components/MvpMenu/mvpMenuSlice';
import ormReducer from './ormSlice';

const persistConfig = {
  key: 'reduxStates',
  storage,
};

const reducers = combineReducers({
  mvpMenu: mvnMenuReducer,
  orm: ormReducer,
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
