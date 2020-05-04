import { ORM, createReducer } from 'redux-orm';
import Mvp from '../models/Mvp';
import Sprint from '../models/Sprint';
import Team from '../models/Team';
import Technology from '../models/Technology';
import TeamMmeber from '../models/TeamMember';

const orm = new ORM({
  stateSelector: (state) => state.orm,
});

orm.register(Mvp);
orm.register(Sprint);
orm.register(Team);
orm.register(Technology);
orm.register(TeamMmeber);
const ormReducer = createReducer(orm, function (session, action) {
  session.sessionBoundModels.forEach((modelClass) => {
    if (modelClass.slice && typeof modelClass.slice.reducer === 'function') {
      modelClass.slice.reducer(modelClass, action, session);
    }
  });
});

export default ormReducer;
export { orm };
