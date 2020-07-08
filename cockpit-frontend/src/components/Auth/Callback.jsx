import React from 'react';
import { AuthConsumer } from '../../services/authProvider';

export const Callback = () => (
  <AuthConsumer>
    {({ signinRedirectCallback }) => {
      signinRedirectCallback();
      return <span>Callback loading</span>;
    }}
  </AuthConsumer>
);
