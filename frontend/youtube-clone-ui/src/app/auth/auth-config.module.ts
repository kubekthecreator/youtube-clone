import { NgModule } from '@angular/core';
import { AuthModule } from 'angular-auth-oidc-client';
import {WindowService} from "../window.service";


// @ts-ignore
const authConfig: AuthConfig = {
  config: {
    authority: 'https://dev-3cp642wab605pwuc.us.auth0.com',
    redirectUrl: '', // Tu zostawiamy puste, aby przypisać wartość później
    postLogoutRedirectUri: '', // Tu zostawiamy puste, aby przypisać wartość później
    clientId: 'YVjfzIjGl3Be1EvjcGnuLnJSKwv8zlNU',
    scope: 'openid profile offline_access',
    responseType: 'code',
    silentRenew: true,
    useRefreshToken: true,
  }
};

@NgModule({
  imports: [AuthModule.forRoot(authConfig)],
  exports: [AuthModule],
  providers: [WindowService] // Dodajemy WindowService jako dostawcę
})
export class AuthConfigModule {
  constructor(private windowService: WindowService) {
    // Pobieramy window.location.origin za pomocą WindowService
    authConfig.config.redirectUrl = this.windowService.getLocation().origin;
    authConfig.config.postLogoutRedirectUri = this.windowService.getLocation().origin;
  }
}
