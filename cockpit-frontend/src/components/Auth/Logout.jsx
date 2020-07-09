import React from 'react';
import { AuthConsumer } from '../../services/authProvider';

export const Logout = () => (
  <AuthConsumer>
    {({ logout }) => {
      logout();
      return <span>Logout Page</span>;
    }}
  </AuthConsumer>
);
