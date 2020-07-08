import { UserManager, WebStorageStateStore, Log } from 'oidc-client';
import { useHistory } from 'react-router-dom';
import authConfig from '../common/utils/authConfig';

export default class AuthService {
  UserManager;

  constructor() {
    const stage = process.env.REACT_APP_STAGE === 'prod' ? 'prod' : 'preprod';
    this.UserManager = new UserManager({
      ...authConfig[stage],
      userStore: new WebStorageStateStore({ store: window.sessionStorage }),
    });
    Log.logger = console;
    Log.level = Log.DEBUG;
    this.UserManager.events.addUserLoaded(() => {
      if (window.location.href.indexOf('signin-oidc') !== -1) {
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
    console.log('1');
    this.UserManager.signinRedirectCallback().then(() => {
      '';
    });
  };

  getUser = async () => {
    console.log('2');
    const user = await this.UserManager.getUser();
    if (!user) {
      return await this.UserManager.signinRedirectCallback();
    }
    return user;
  };

  parseJwt = (token) => {
    console.log('3');
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
  };

  signinRedirect = () => {
    console.log('4');
    localStorage.setItem('redirectUri', window.location.pathname);
    this.UserManager.signinRedirect({});
  };

  isAuthenticated = () => {
    console.log('5');
    const oidcStorage = JSON.parse(
      sessionStorage.getItem(
        `oidc.user:${process.env.REACT_APP_AUTH_URL}:${process.env.REACT_APP_IDENTITY_CLIENT_ID}`,
      ),
    );

    return !!oidcStorage && !!oidcStorage.access_token;
  };

  navigateToHomePage = () => {
    console.log('6');

    /*
    const history = useHistory();
    const path = '/';
    console.log('Here 3');
    history.push(path); */
    window.location.replace('/');
  };

  signinSilent = () => {
    console.log('7');
    this.UserManager.signinSilent()
      .then((user) => {
        console.log('signed in', user);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  signinSilentCallback = () => {
    console.log('8');
    this.UserManager.signinSilentCallback();
  };

  createSigninRequest = () => {
    console.log('9');
    return this.UserManager.createSigninRequest();
  };

  logout = () => {
    console.log('10');
    this.UserManager.signoutRedirect({
      id_token_hint: localStorage.getItem('id_token'),
    });
    this.UserManager.clearStaleState();
  };

  signoutRedirectCallback = () => {
    console.log('11');
    this.UserManager.signoutRedirectCallback().then(() => {
      localStorage.clear();
      window.location.replace(process.env.REACT_APP_PUBLIC_URL);
    });
    this.UserManager.clearStaleState();
  };
}
