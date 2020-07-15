import React from 'react';
import { AuthConsumer } from '../../services/authProvider';

export const Callback = () => (
  <AuthConsumer>
    {({ signinRedirectCallback }) => {
      signinRedirectCallback();
      // eslint-disable-next-line no-debugger
      debugger;
      return <span>Login in progress – please wait...</span>;
    }}
  </AuthConsumer>
);
