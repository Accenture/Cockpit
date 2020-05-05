import { createSelector } from 'redux-orm';
import orm from './orm';

export const mvpSelector = createSelector(orm, orm.Mvp);
