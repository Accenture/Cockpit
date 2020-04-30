import { Model, attr, ORM, fk, many } from 'redux-orm';

export class Mvp extends Model {
  static get fields() {
    return {
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
  }

  static reducer(action, mvp) {
    switch (action.type) {
      case 'SAVE_MVPS': {
        action.payload.map((item) => mvp.create(item));
        break;
      }
      default:
        break;
    }
  }
}
Mvp.modelName = 'Mvp';

export const orm = new ORM();
orm.register(Mvp);
