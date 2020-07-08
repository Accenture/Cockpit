import React from 'react';
import { Route } from 'react-router-dom';
import { AuthConsumer } from '../services/authProvider';

export const PrivateRoute = ({ component, ...rest }) => {
  const renderFn = (Component) => (props) => (
    <AuthConsumer>
      {({ isAuthenticated, signinRedirect }) => {
                console.log(!!Component && isAuthenticated());
        if (!!Component && isAuthenticated()) {
          return <Component {...props} />;
        }
        signinRedirect();
        return <span>loading home page</span>;
      }}
    </AuthConsumer>
  );

  return <Route {...rest} render={renderFn(component)} />;
};
