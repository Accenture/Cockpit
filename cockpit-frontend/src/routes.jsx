import React, { Suspense } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import HomePage from './components/HomePage/HomePage';
import MvpInfoPage from './components/MvpInfoPage/MvpInfoPage';
import { Callback } from './components/Auth/Callback';
import { Logout } from './components/Auth/Logout';
import { LogoutCallback } from './components/Auth/LogoutCallBack';
import { SilentRenew } from './components/Auth/SilentRenew';
import { PrivateRoute } from './routes/PrivateRoute';

function Routes() {
  const env = process.env.REACT_APP_STAGE;
  function isNotLocal() {
    return env === 'dev' || env === 'qa' || env === 'prod';
  }
  return (
    <Router>
      <Suspense fallback={<HomePage />}>
        <Switch>
          {isNotLocal() ? (
            <PrivateRoute exact path="/" component={HomePage} />
          ) : (
            <Route exact path="/" component={HomePage} />
          )}
          {isNotLocal() ? (
            <PrivateRoute exact path="/mvp-info/:id" component={MvpInfoPage} />
          ) : (
            <Route exact path="/mvp-info/:id" component={MvpInfoPage} />
          )}
          <Route exact path="/authentication" component={Callback} />
          <Route exact path="/logout" component={Logout} />
          <Route exact path="/logout/callback" component={LogoutCallback} />
          <Route exact path="/silentrenew" component={SilentRenew} />
        </Switch>
      </Suspense>
    </Router>
  );
}

export default Routes;
