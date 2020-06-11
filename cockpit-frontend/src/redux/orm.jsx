import { ORM } from 'redux-orm';
import Mvp from '../models/Mvp';
import Sprint from '../models/Sprint';
import Jira from '../models/Jira';
import Team from '../models/Team';
import Technology from '../models/Technology';
import TeamMmeber from '../models/TeamMember';

const orm = new ORM({
  stateSelector: (state) => state.orm,
});

orm.register(Mvp, Jira, Sprint, Team, Technology, TeamMmeber);

export default orm;
