import { Model, attr, ORM, many } from 'redux-orm';

export class Team extends Model {
  static get fields() {
    return {
      id: attr(),
      name: attr(),
      teamMembers: many('TeamMember'),
    };
  }

  static reducer(action, team) {
    switch (action.type) {
      case 'CREATE_TEAM': {
        team.create(action.payload);
        break;
      }
      default:
        break;
    }
  }
}
Team.modelName = 'Team';

export const orm = new ORM();
orm.register(Team);
