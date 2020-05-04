import React from 'react';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';
import TestAxios from '../testAxios/TestAxios';

function HomePage() {
  return (
    <div>
      <Header />
      <MvpTabs />
      <TestAxios />
    </div>
  );
}

export default HomePage;
