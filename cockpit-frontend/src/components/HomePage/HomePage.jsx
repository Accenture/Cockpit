import React from 'react';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';
import './HomePage.scss';

function HomePage() {
  return (
    <div>
      <Header />
      <MvpTabs />
    </div>
  );
}

export default HomePage;
