import React, { Component } from 'react';
// import MvpService from '../../services/service';
import { fetchMvps } from '../../actions/action';

export default class TestAxios extends Component {
  // eslint-disable-next-line no-useless-constructor
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    /* get all MVPs */
    try {
      // eslint-disable-next-line react/destructuring-assignment
      fetchMvps();
    } catch (e) {
      console.log(`😱 Axios request failed: ${e}`);
    }
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
  }

  render() {
    return <div />;
  }
}
