import { Model, attr } from 'redux-orm';

export class TeamMember extends Model {
  toString() {
    return `TeamMember: ${this.name}`;
  }
}
TeamMember.modelName = 'TeamMember';

TeamMember.fields = {
  idMember: attr(),
  firstName: attr(),
  lastName: attr(),
  email: attr(),
};

export default TeamMember;
