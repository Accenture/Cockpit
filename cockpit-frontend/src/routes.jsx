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
  return (
    <Router>
      <Suspense fallback={<HomePage />}>
        <Switch>
          <PrivateRoute exact path="/" component={HomePage} />
          <PrivateRoute exact path="/mvp-info/:id" component={MvpInfoPage} />
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
