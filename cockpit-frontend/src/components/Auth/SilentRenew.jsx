import React from 'react';
import { AuthConsumer } from '../../services/authProvider';

export const SilentRenew = () => (
  <AuthConsumer>
    {({ signinSilentCallback }) => {
      signinSilentCallback();
      return <span>Renewing the token</span>;
    }}
  </AuthConsumer>
);
