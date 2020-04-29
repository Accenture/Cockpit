import React from 'react';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';
import Card from '../Card/MvpCard';
import './HomePage.scss';

function HomePage() {
  return (
    <div className="mvp-overview-container">
      <Header />
      <MvpTabs />
      <Card />
    </div>
  );
}

export default HomePage;
