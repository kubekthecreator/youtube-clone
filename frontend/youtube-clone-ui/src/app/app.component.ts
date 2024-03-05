import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import defaultLanguage from "./../assets/i18n/en.json";
import {LoginResponse, OidcSecurityService} from "angular-auth-oidc-client";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  constructor(private translate: TranslateService, private oidcSecurityService: OidcSecurityService) {
    translate.setTranslation('en', defaultLanguage);
    translate.setDefaultLang('en');
  }

  ngOnInit(): void {
    this.oidcSecurityService
      .checkAuth()
      .subscribe((loginResponse: LoginResponse) => {
        const { isAuthenticated} =
          loginResponse;
      });
    }
  title = 'youtube-clone-ui';

  useLanguage(language: string): void {
    this.translate.use(language);
  }
}
