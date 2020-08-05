import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';
import ScrumMasterForm from '../ScrumMasterForm/ScrumMasterForm';
import MvpService from '../../services/apiService';
import { isScrumMasterState, setUserIsScrumMaster } from './HomePageSlice';

function HomePage() {
  const isSM = useSelector(isScrumMasterState);
  const dispatch = useDispatch();
  useEffect(() => {
    async function isScrumMaster() {
      if (isSM === null) {
        const result = await MvpService.isUserScrumMaster();
        dispatch(setUserIsScrumMaster(result.data));
      }
    }
    isScrumMaster();
  }, [dispatch, isSM]);
  return (
    <div>
      <Header />
      <MvpTabs />
      <ScrumMasterForm />
    </div>
  );
}

export default HomePage;
