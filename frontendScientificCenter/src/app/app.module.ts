import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { JournalComponent } from './journal/journal.component';
import { HomeComponent } from './home/home.component';
import { AddEditorsComponent } from './add-editors/add-editors.component';
import { LoginComponent } from './login/login.component';
import { httpInterceptorProviders } from './auth/auth-interceptor';
import { TaskComponent } from './task/task.component';
import { MultiSelectAllModule } from '@syncfusion/ej2-angular-dropdowns';

const appRoutes: Routes = [
  {path: 'register', component : RegistrationComponent},
  {path: 'confirm/:username/:id', component : ConfirmComponent},
  {path: 'journal', component : JournalComponent},
  {path: 'addEditors/:id', component : AddEditorsComponent},
  {path: 'login', component : LoginComponent},
  {path: '', component : HomeComponent},
  {path: 'task/:taskId', component : TaskComponent},
  ]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    ConfirmComponent,
    JournalComponent,
    HomeComponent,
    AddEditorsComponent,
    LoginComponent,
    TaskComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MultiSelectAllModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing : true}
    ),
    
  ],
  providers: [httpInterceptorProviders,],
  bootstrap: [AppComponent]
})
export class AppModule { }

