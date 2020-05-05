import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchAllMvps } from '../../redux/ormSlice';
import { mvpSelector } from '../../redux/selector';

export default function TestAxios() {
  const dispatch = useDispatch();

  /* get all MVPs */

  const mvps = useSelector((state) => mvpSelector(state));
  console.log(mvps);
  const mvp = useSelector((state) => mvpSelector(state, 'COC'));
  console.log(mvp);

  /* get light MVPs 
    try {
      await MvpService.getLightMvp().then((response) => {
        console.log(`get light MVPs : \n ${JSON.stringify(response)} `);
      });
    } catch (e) {
      console.log(`😱 Axios request failed: ${e}`);
    }
    /* get MVP by ID 
    try {
      const id = 'COC';
      await MvpService.getOne(id).then((response) => {
        console.log(`get  MVP by id : \n ${JSON.stringify(response)} `);
      });
    } catch (e) {
      console.log(`😱 Axios request failed: ${e}`);
    }
    /* update  MVP 
    try {
      const id = 'COC';
      const mvp = {
        location: 'Brest',
        nbSprint: 0,
      };
      await MvpService.updateOne(id, mvp).then((response) => {
        console.log(`update MVP : \n ${JSON.stringify(response)} `);
      });
    } catch (e) {
      console.log(`😱 Axios request failed: ${e}`);
    } */

  return (
    <div>
      <button
        onClick={() => {
          dispatch(fetchAllMvps());
        }}
      >
        {' '}
        display mvps in console{' '}
      </button>{' '}
    </div>
  );
}
