import { Model, attr } from 'redux-orm';

export class Technology extends Model {
  toString() {
    return `Technology: ${this.name}`;
  }
}
Technology.modelName = 'Technology';

Technology.fields = {
  name: attr(),
  logoUrl: attr(),
};

export default Technology;
