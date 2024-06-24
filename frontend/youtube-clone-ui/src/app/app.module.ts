import {NgModule} from '@angular/core';
import {BrowserModule, provideClientHydration} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {provideAnimationsAsync} from '@angular/platform-browser/animations/async';
import {UploadVideoComponent} from './upload-video/upload-video.component';
import {HttpClient, provideHttpClient, withInterceptors} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxFileDropModule} from 'ngx-file-drop';
import {TranslateCompiler, TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {TranslateMessageFormatCompiler} from 'ngx-translate-messageformat-compiler';
import {MatButton, MatIconButton, MatMiniFabButton} from "@angular/material/button";
import {HeaderComponent} from './header/header.component';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIcon} from "@angular/material/icon";
import {SaveVideoDetailsComponent} from './save-video-details/save-video-details.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatLine, MatOptionModule} from "@angular/material/core";
import {MatInputModule} from "@angular/material/input";
import {FlexLayoutServerModule} from "@angular/flex-layout/server";
import {MatChip, MatChipGrid, MatChipInput, MatChipListbox, MatChipRow} from "@angular/material/chips";
import {VgBufferingModule} from "@videogular/ngx-videogular/buffering";
import {VgCoreModule} from "@videogular/ngx-videogular/core";
import {VgControlsModule} from "@videogular/ngx-videogular/controls";
import {VgOverlayPlayModule} from "@videogular/ngx-videogular/overlay-play";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatProgressBar} from "@angular/material/progress-bar";
import {MatCardModule} from "@angular/material/card";
import {VideoPlayerComponent} from './video-player/video-player.component';
import {authInterceptor, provideAuth} from "angular-auth-oidc-client";
import { VideoDetailComponent } from './video-detail/video-detail.component';
import { HomeComponent } from './home/home.component';
import { HistoryComponent } from './history/history.component';
import { SubscriptionsComponent } from './subscriptions/subscriptions.component';
import { LikedVideosComponent } from './liked-videos/liked-videos.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import {MatSidenav, MatSidenavContainer} from "@angular/material/sidenav";
import {MatList, MatListItem, MatListItemIcon, MatNavList} from "@angular/material/list";
import { FeaturedComponent } from './featured/featured.component';
import { VideoCardComponent } from './video-card/video-card.component';
import {MatDivider} from "@angular/material/divider";
import { CallbackComponent } from './callback/callback.component';
import { CommentsComponent } from './comments/comments.component';
import {MatMenu, MatMenuItem} from "@angular/material/menu";

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    HeaderComponent,
    SaveVideoDetailsComponent,
    VideoPlayerComponent,
    VideoDetailComponent,
    HomeComponent,
    HistoryComponent,
    SubscriptionsComponent,
    LikedVideosComponent,
    SidebarComponent,
    FeaturedComponent,
    VideoCardComponent,
    CallbackComponent,
    CommentsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NgxFileDropModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      compiler: {
        provide: TranslateCompiler,
        useClass: TranslateMessageFormatCompiler
      }
    }),
    MatButton,
    MatToolbarModule,
    MatIcon,
    MatIconButton,
    FlexLayoutModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    ReactiveFormsModule,
    FlexLayoutServerModule,
    MatChipGrid,
    MatChipRow,
    MatChipInput,
    VgCoreModule,
    VgControlsModule,
    VgOverlayPlayModule,
    VgBufferingModule,
    MatSnackBarModule,
    MatMiniFabButton,
    MatProgressBar,
    MatCardModule,
    MatSidenavContainer,
    MatSidenav,
    MatList,
    MatListItem,
    MatNavList,
    MatLine,
    MatListItemIcon,
    MatDivider,
    MatChip,
    MatChipListbox,
    MatMenu,
    MatMenuItem,
  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync(),
    provideHttpClient(withInterceptors([authInterceptor()])),
    provideAuth({
      config: {
        authority: 'https://dev-3cp642wab605pwuc.us.auth0.com',
        redirectUrl: window.location.origin,
        postLogoutRedirectUri: window.location.origin,
        clientId: 'YVjfzIjGl3Be1EvjcGnuLnJSKwv8zlNU',
        scope: 'openid profile offline_access email',
        responseType: 'code',
        silentRenew: true,
        useRefreshToken: true,
        secureRoutes: ['http://localhost:8080/'],
        customParamsAuthRequest: {
          audience: 'http://localhost:8080'
        },
      },
    }),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
