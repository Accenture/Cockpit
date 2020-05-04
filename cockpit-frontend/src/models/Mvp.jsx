import { Model, attr, fk, many } from 'redux-orm';

export class Mvp extends Model {
  toString() {
    return `Mvp: ${this.name}`;
  }
}
Mvp.modelName = 'Mvp';

Mvp.fields = {
  id: attr(),
  name: attr(),
  pitch: attr(),
  status: attr(),
  entity: attr(),
  currentSprint: attr(),
  nbSprint: attr(),
  mvpEndDate: attr(),
  mvpStartDate: attr(),
  mvpAvatarUrl: attr(),
  location: attr(),
  nbUserStories: attr(),
  bugsCount: attr(),
  allBugsCount: attr(),
  timeToFix: attr(),
  timeToDetect: attr(),
  technicalDebt: attr(),
  sharedMVPId: attr(),
  iterationNumber: attr(),
  teamId: fk({
    to: 'Team',
    as: 'team',
    relatedName: 'mvps',
  }),
  sprints: many('Sprint'),
  technologies: many('Technology'),
};

export default Mvp;
