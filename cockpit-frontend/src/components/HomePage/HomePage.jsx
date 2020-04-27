import React from 'react';
import Header from '../Header/Header';
import './HomePage.scss';

function HomePage() {
  return (
    <div className="App">
      <Header />
      <div className="mvp-overview-container">Cockpit MVP Dashboard</div>
    </div>
  );
}

export default HomePage;
