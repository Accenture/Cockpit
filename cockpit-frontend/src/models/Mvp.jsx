import { Model } from 'redux-orm';

export class Mvp extends Model {
  toString() {
    return `Mvp: ${this.name}`;
  }
}
Mvp.modelName = 'Mvp';

/*
Mvp.fields = {
  id: attr(),
  name: attr(),
  entity: attr(),
  urlMvpAvatar: attr(),
  cycle: attr(),
  mvpDescription: attr(),
  status: attr(),
  team: fk({
    to: 'Team',
    as: 'team',
    relatedName: 'mvps',
  }),
  technologies: many({
    to: 'Technology',
    as: 'technology',
    relatedName: 'mvps',
  }),
};
*/

export default Mvp;
