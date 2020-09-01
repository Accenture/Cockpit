const AUTH_CONFIG = {
  dev: {
    client_id: 'cockpit-dev',
    redirect_uri: 'https://azwbdcokp01.azurewebsites.net/authentication',
    response_type: 'id_token token',
    scope: 'openid email groups',
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
    },
  },
  qa: {
    client_id: 'cockpit-qa',
    redirect_uri: 'https://azwbqcokp01.azurewebsites.net/authentication',
    response_type: 'id_token token',
    scope: 'openid email groups',
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
    },
  },
  // Prod config to be updated by Digital Pass Prod
  prod: {
    client_id: 'cockpit-prod',
    redirect_uri: 'https://azwbpcokp01.azurewebsites.net/authentication',
    response_type: 'id_token token',
    scope: 'openid email groups',
    load_user_info: false,
    authority: 'https://sso-digitalpassport.hubtotal.net/sso/oauth2/total',
    metadata: {
      issuer: 'https://sso-digitalpassport.hubtotal.net/sso',
      end_session_endpoint:
        'https://sso-digitalpassport.hubtotal.net/sso/oauth2/total/connect/endSession',
      check_session_iframe:
        'https://sso-digitalpassport.hubtotal.net/sso/oauth2/total/connect/checkSession',
      authorization_endpoint:
        'https://sso-digitalpassport.hubtotal.net/sso/oauth2/total/authorize?usesso=true',
      userinfo_endpoint:
        'https://api-digitalpassport.hubtotal.net/sso/oauth2/total/userinfo',
      jwks_uri:
        'https://api-digitalpassport.hubtotal.net/sso/oauth2/total/connect/jwk_uri',
    },
  },
};
export default AUTH_CONFIG;
