import React from 'react';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';
import ScrumMasterForm from '../ScrumMasterForm/ScrumMasterForm';

function HomePage() {
  return (
    <div>
      <Header />
      <MvpTabs />
      <ScrumMasterForm />
    </div>
  );
}

export default HomePage;
