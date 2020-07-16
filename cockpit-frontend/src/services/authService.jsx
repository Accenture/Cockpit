import { UserManager, WebStorageStateStore, Log } from 'oidc-client';
import AUTH_CONFIG from '../common/utils/authConfig';

export default class AuthService {
  UserManager;

  constructor() {
    const stage = process.env.REACT_APP_STAGE === 'prod' ? 'prod' : 'preprod';
    this.UserManager = new UserManager({
      ...AUTH_CONFIG[stage],
      userStore: new WebStorageStateStore({ store: window.sessionStorage }),
    });
    Log.logger = console;
    Log.level = Log.DEBUG;
    this.UserManager.events.addUserLoaded(() => {
      if (window.location.href.indexOf('authenticaiton') !== -1) {
        this.navigateToHomePage();
      }
    });
    this.UserManager.events.addSilentRenewError((e) => {
      console.log('silent renew error', e.message);
    });

    this.UserManager.events.addAccessTokenExpired(() => {
      console.log('token expired');
      this.signinSilent();
    });
  }

  signinRedirectCallback = () => {
    this.UserManager.signinRedirectCallback().then(() => {
      '';
    });
  };

  getUser = async () => {
    const user = await this.UserManager.getUser();
    if (!user) {
      // eslint-disable-next-line no-return-await
      return await this.UserManager.signinRedirectCallback();
    }
    return user;
  };

  parseJwt = (token) => {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
  };

  signinRedirect = () => {
    localStorage.setItem('redirectUri', window.location.pathname);
    this.UserManager.signinRedirect({});
  };

  isAuthenticated = () => {
    console.log('User Is authencated');
    const oidcStorage = JSON.parse(
      sessionStorage.getItem(
        `oidc.user:https://external-total.okta.com/oauth2/default:0oajesx698Xd6LGCi4x6`,
      ),
    );
    return !!oidcStorage && !!oidcStorage.access_token;
  };

  navigateToHomePage = () => {
    window.location.replace('/');
  };

  signinSilent = () => {
    this.UserManager.signinSilent()
      .then((user) => {
        console.log('signed in', user);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  signinSilentCallback = () => {
    this.UserManager.signinSilentCallback();
  };

  createSigninRequest = () => {
    return this.UserManager.createSigninRequest();
  };

  logout = () => {
    this.UserManager.signoutRedirect({
      id_token_hint: localStorage.getItem('id_token'),
    });
    this.UserManager.clearStaleState();
  };

  signoutRedirectCallback = () => {
    this.UserManager.signoutRedirectCallback().then(() => {
      localStorage.clear();
      window.location.replace(process.env.REACT_APP_PUBLIC_URL);
    });
    this.UserManager.clearStaleState();
  };
}
