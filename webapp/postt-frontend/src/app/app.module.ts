import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { RouterModule } from '@angular/router';
import { Error404Component } from './error-404/error-404.component';
import { PostitModule } from './postit/postit.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    Error404Component
  ],
  imports: [
    BrowserModule,
    CoreModule,
    PostitModule,
    HttpClientModule,
    RouterModule.forRoot([
      {
        path:'', redirectTo:'home', pathMatch: 'full'
      },
      {
        path:'**', component: Error404Component
      }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
