import { FETCH_MVPS } from './types';
import MvpService from '../services/service';

export const getMvps = () => (dispatch) => {
  MvpService.getAll()
    .then((response) => response.data)
    .then((mvps) =>
      dispatch({
        type: FETCH_MVPS,
        payload: mvps,
      }),
    );
};
