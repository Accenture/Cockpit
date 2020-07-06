import { UserManager, WebStorageStateStore, Log } from "oidc-client";
import authConfig from '../common/utils/authConfig';

export default class AuthService {
  constructor() {
    const stage = process.env.REACT_APP_STAGE === 'prod' ? 'prod' : 'preprod';
    const userManager = new UserManager(authConfig[stage]);
    userManager.events.addUserLoaded((user) => {
      if (window.location.href.indexOf('signin-oidc') !== -1) {
        this.navigateToScreen();
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
}
