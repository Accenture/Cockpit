import { UserManager, User, WebStorageStateStore, Log } from 'oidc-client';
import AUTH_CONFIG from '../common/utils/authConfig';

export default class AuthService {
  UserManager;

  User;

  constructor() {
    const stage = process.env.REACT_APP_STAGE === 'prod' ? 'prod' : 'preprod';
    this.UserManager = new UserManager({
      ...AUTH_CONFIG[stage],
      userStore: new WebStorageStateStore({ store: window.sessionStorage }),
    });
    Log.logger = console;
    Log.level = Log.DEBUG;
    this.UserManager.events.addUserLoaded(() => {
      if (window.location.href.indexOf('authentication') !== -1) {
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
    this.UserManager.signinRedirectCallback().then((user) => {
      // DigitalPass is missing CORS Headers for request to succeed
      this.User = user;
    });
  };

  getUser = async () => {
    console.log('Getting users ....');
    this.User = new User();
    const user = await this.UserManager.getUser().then((u) => {
      this.User = u;
    });
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
    this.UserManager.signinRedirect({});
  };

  isAuthenticated = () => {
    console.log('User Is authencated');
    const idToken = sessionStorage.getItem('id_token');
    console.log('User is authenticated');
    return !!idToken;
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
      id_token_hint: sessionStorage.getItem('id_token'),
    });
    this.UserManager.clearStaleState();
  };

  signoutRedirectCallback = () => {
    this.UserManager.signoutRedirectCallback().then(() => {
      sessionStorage.clear();
      window.location.replace(process.env.REACT_APP_PUBLIC_URL);
    });
    this.UserManager.clearStaleState();
  };
}
