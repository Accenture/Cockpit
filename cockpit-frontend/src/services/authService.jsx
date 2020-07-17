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
    this.UserManager.events.addUserLoaded(() => {
      if (window.location.href.indexOf('authentication') !== -1) {
        this.navigateToHomePage();
      }
    });
    this.UserManager.events.addSilentRenewError((e) => {
      console.log('Silent renew error', e.message);
    });

    this.UserManager.events.addAccessTokenExpired(() => {
      console.log('Token expired');
      this.signinSilent();
    });
  }

  signinRedirectCallback = () => {
    this.UserManager.signinRedirectCallback().then((user) => {
      console.log('Signin redirect callback get user');
      // DigitalPass is missing CORS Headers for request to succeed
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

  signinRedirect = () => {
    this.UserManager.signinRedirect({});
  };

  isAuthenticated = () => {
    const idToken = sessionStorage.getItem('id_token');
    if (idToken != null) {
    const tokenBody = idToken.split('.')[1];
      const decodedToken = JSON.parse(window.atob(tokenBody));
      if (decodedToken.exp && decodedToken.exp * 1000 > Date.now()) {
        return true;
      }
    }
    return false;
  };

  navigateToHomePage = () => {
    window.location.replace('/');
  };

  createSigninRequest = () => {
    return this.UserManager.createSigninRequest();
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
