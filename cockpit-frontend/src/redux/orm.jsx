import { ORM } from 'redux-orm';
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

export default orm;
