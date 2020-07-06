const authConfig = {
  preprod: {
    client_id: '0oajesx698Xd6LGCi4x6',
    redirect_uri: `${window.location.protocol}//${window.location.hostname}${
      window.location.port ? `:${window.location.port}` : ''
    }/login/oauth2/code/okta`,
    // post_logout_redirect_uri: `${window.location.protocol}//${window.location.hostname}:${window.location.port}/`,
    response_type: 'token',
    scope: 'openid',
    authority: 'https://external-total.okta.com/oauth2/default',
  },
  // Prod config to be modified
  prod: {
    client_id: '0oajesx698Xd6LGCi4x6',
    redirect_uri: `${window.location.protocol}//${window.location.hostname}${
      window.location.port ? `:${window.location.port}` : ''
    }/login/oauth2/code/okta`,
    // post_logout_redirect_uri: `${window.location.protocol}//${window.location.hostname}:${window.location.port}/`,
    response_type: 'token',
    scope: 'openid',
    authority: 'https://external-total.okta.com/oauth2/default',
  },
};

export default authConfig;
