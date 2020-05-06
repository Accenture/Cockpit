import { Model, attr } from 'redux-orm';

export class TeamMember extends Model {
  toString() {
    return `TeamMember: ${this.name}`;
  }
}
TeamMember.modelName = 'TeamMember';

TeamMember.fields = {
  idMember: attr(),
  lastName: attr(),
  firstName: attr(),
  emailId: attr(),
  avatarUrl: attr(),
  idRole: attr(),
};

export default TeamMember;
