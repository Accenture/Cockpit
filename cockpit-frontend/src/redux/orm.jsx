import { ORM } from 'redux-orm';
import Mvp from '../models/Mvp';

const orm = new ORM({
  stateSelector: (state) => state.orm,
});

orm.register(Mvp);

export default orm;
