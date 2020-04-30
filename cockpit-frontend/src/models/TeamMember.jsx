import { Model, attr, ORM } from 'redux-orm';

export class TeamMember extends Model {
  static get fields() {
    return {
      idMember: attr(),
      lastName: attr(),
      firstName: attr(),
      emailId: attr(),
      avatarUrl: attr(),
      idRole: attr(),
    };
  }
}
TeamMember.modelName = 'TeamMember';

export const orm = new ORM();
orm.register(TeamMember);
