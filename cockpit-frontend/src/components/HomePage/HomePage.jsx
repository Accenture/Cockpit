import React from 'react';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';

function HomePage() {
  return (
    <div className="mvp-overview-container">
      <Header />
      <MvpTabs />
    </div>
  );
}

export default HomePage;
