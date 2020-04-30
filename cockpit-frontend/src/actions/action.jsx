import { FETCH_MVPS } from './types';
import MvpService from '../services/service';

export const fetchMvps = () => (dispatch) => {
  MvpService.getAll()
    .then((response) => response.json())
    .then((mvps) =>
      dispatch({
        type: FETCH_MVPS,
        payload: mvps,
      }),
    );
};
