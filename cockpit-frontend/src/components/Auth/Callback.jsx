import React, { useEffect } from 'react';
import { AuthConsumer } from '../../services/authProvider';

export function Callback() {
  function setUserToSessionStorage() {
    const hash = window.location.href.split('#');
    // Parse access token and id token form redirect url
    if (hash.length > 1) {
      const params = hash[1].split('&');
      if (params.length > 0) {
        const idTokenParam = params.find((x) => x.startsWith('id_token'));
        const accessTokenParam = params.find((x) =>
          x.startsWith('access_token'),
        );
        const idToken = idTokenParam.split('=')[1];
        const accessToken = accessTokenParam.split('=')[1];
        // Store id token in session storage
        if (idToken != null) {
          window.sessionStorage.setItem('id_token', idToken);
        }
        // Store access token in session storage
        if (accessToken != null) {
          window.sessionStorage.setItem('access_token', accessToken);
        }
      }
    }
  }

  useEffect(() => {
    setUserToSessionStorage();
    window.location.replace('/');
  }, []);

  return (
    <AuthConsumer>
      {({ signinRedirectCallback }) => {
        signinRedirectCallback();
        return <span>Login in progress – please wait...</span>;
      }}
    </AuthConsumer>
  );
}
