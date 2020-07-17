const AUTH_CONFIG = {
  preprod: {
    client_id: 'cockpit-dev',
    /*
      redirect_uri: `${window.location.protocol}//${window.location.hostname}${
        window.location.port ? `:${window.location.port}` : ''
      }/login/oauth2/code/okta`,
      */
    redirect_uri: 'https://azwbdcokp01.azurewebsites.net/authentication',
    // post_logout_redirect_uri: `${window.location.protocol}//${window.location.hostname}:${window.location.port}/`,
    response_type: 'id_token token',
    scope: 'openid email',
    load_user_info: false,
    authority: 'https://pp-sso-digitalpassport.hubtotal.net/sso/oauth2/total',
    metadata: {
      issuer: 'https://pp-sso-digitalpassport.hubtotal.net/sso',
      end_session_endpoint:
        'https://pp-sso-digitalpassport.hubtotal.net/sso/oauth2/total/connect/endSession',
      check_session_iframe:
        'https://pp-sso-digitalpassport.hubtotal.net/sso/oauth2/total/connect/checkSession',
      authorization_endpoint:
        'https://pp-sso-digitalpassport.hubtotal.net/sso/oauth2/total/authorize?usesso=true',
      userinfo_endpoint:
        'https://pp-api-digitalpassport.hubtotal.net/sso/oauth2/total/userinfo',
      jwks_uri:
        'https://pp-api-digitalpassport.hubtotal.net/sso/oauth2/total/connect/jwk_uri',
      registration_endpoint:
        'https://pp-sso-digitalpassport.hubtotal.net/sso/oauth2/total/connect/register',
    },
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
export default AUTH_CONFIG;
