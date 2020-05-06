import { Model, attr, many } from 'redux-orm';

export class Team extends Model {
  toString() {
    return `Team: ${this.name}`;
  }
}
Team.modelName = 'Team';

Team.fields = {
  id: attr(),
  name: attr(),
  teamMembers: many('TeamMember'),
};

export default Team;
