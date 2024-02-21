import { Component } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import defaultLanguage from "./../assets/i18n/en.json";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  constructor(private translate: TranslateService) {
    translate.setTranslation('en', defaultLanguage);
    translate.setDefaultLang('en');
  }
  title = 'youtube-clone-ui';

  useLanguage(language: string): void {
    this.translate.use(language);
  }
}
