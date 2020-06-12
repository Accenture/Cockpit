import { Model, attr, fk, many, oneToOne } from 'redux-orm';

export class Mvp extends Model {
  toString() {
    return `Mvp: ${this.name}`;
  }
}
Mvp.modelName = 'Mvp';

Mvp.fields = {
  id: attr(),
  name: attr(),
  entity: attr(),
  urlMvpAvatar: attr(),
  cycle: attr(),
  mvpDescription: attr(),
  status: attr(),
  idTeam: fk('Team', 'mvps'),
  jira: oneToOne('Jira'),
  technologies: many('Technology'),
};

export default Mvp;
