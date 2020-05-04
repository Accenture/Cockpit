import { Model, attr } from 'redux-orm';
import { createSlice } from '@reduxjs/toolkit';

export class Technology extends Model {
  static modelName = 'Technology';

  static get fields() {
    return {
      name: attr(),
      logoUrl: attr(),
    };
  }

  static slice = createSlice({
    name: 'technoSlice',
    initialState: undefined,
    reducers: {
      saveTechno(Technology, action) {
        Technology.create(action.payload);
      },
    },
  });

  toString() {
    return `Technology: ${this.name}`;
  }
}
export default Technology;
export const { saveTechno } = Technology.slice.actions;
