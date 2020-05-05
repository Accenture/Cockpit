import React from 'react';
import Header from '../Header/Header';
import MvpTabs from '../MvpTabPanel/MvpTabPanel';
import TestAxios from '../testAxios/TestAxios';
import Card from '../Card/MvpCard';
import './HomePage.scss';

function HomePage() {
  return (
    <div>
      <Header />
      <MvpTabs />
      <TestAxios />
      <Card />
    </div>
  );
}

export default HomePage;
