import React, { useEffect } from 'react';
import { AuthConsumer } from '../../services/authProvider';

export function Callback() {
  function setUserToSessionStorage() {
    const hash = window.location.href.split('#');
    if (hash.length > 1) {
      const param = hash[1].split('&');
      if (param.length > 0) {
        const idTokenParam = param.find((x) => x.startsWith('id_token'));
        if (idTokenParam != null && idTokenParam.length > 0) {
          const idToken = idTokenParam.split('=');
          if (
            idToken.length > 1 &&
            idToken[1] != null &&
            idToken[1].length > 0
          ) {
            window.sessionStorage.setItem('id_token', idToken[1]);
          }
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
