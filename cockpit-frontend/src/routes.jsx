import React from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import HomePage from './components/HomePage/HomePage';
import MvpInfoPage from './components/MvpInfoPage/MvpInfoPage';

function Routes() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={HomePage} />
        <Route exact path="/mvp-info/:id" component={MvpInfoPage} />
      </Switch>
    </Router>
  );
}

export default Routes;
