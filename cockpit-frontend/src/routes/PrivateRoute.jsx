import React from 'react';
import { Route } from 'react-router-dom';
import { AuthConsumer } from '../services/authProvider';

export const PrivateRoute = ({ component, ...rest }) => {
  const renderFn = (Component) => (props) => (
    <AuthConsumer>
      {({ isAuthenticated, signinRedirect }) => {
        if (!!Component && isAuthenticated()) {
          return <Component {...props} />;
        }
        signinRedirect();
        return <span>Loading</span>;
      }}
    </AuthConsumer>
  );

  return <Route {...rest} render={renderFn(component)} />;
};
