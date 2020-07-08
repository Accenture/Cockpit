const authConfig = {
  preprod: {
    client_id: '0oajesx698Xd6LGCi4x6',
    redirect_uri: `${window.location.protocol}//${window.location.hostname}${
      window.location.port ? `:${window.location.port}` : ''
    }/login/oauth2/code/okta`,
    // redirect_uri: 'http://localhost:3000/login/oauth2/code/okta',
    post_logout_redirect_uri: `${window.location.protocol}//${window.location.hostname}:${window.location.port}/`,
    response_type: 'code',
    scope: 'openid profile',
    // grantType: 'password',
    load_user_info: true,
    web_auth_response_type: 'id_token token',
    authority: 'https://external-total.okta.com/oauth2/default',
  },
  // Prod config to be modified
  prod: {
    client_id: '0oajesx698Xd6LGCi4x6',
    redirect_uri: `${window.location.protocol}//${window.location.hostname}${
      window.location.port ? `:${window.location.port}` : ''
    }/login/oauth2/code/okta`,
    // post_logout_redirect_uri: `${window.location.protocol}//${window.location.hostname}:${window.location.port}/`,
    response_type: 'id_token token',
    scope: 'openid',
    authority: 'https://external-total.okta.com/oauth2/default',
  },
};

export default authConfig;
