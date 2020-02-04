import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegistrationComponent } from './registration/registration.component';
import { ConfirmComponent } from './confirm/confirm.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { httpInterceptorProviders } from './auth/auth-interceptor';
import { TaskComponent } from './task/task.component';
import { MultiSelectAllModule } from '@syncfusion/ej2-angular-dropdowns';
import { PaymentErrorComponent } from './payment-error/payment-error.component';
import { PaymentSuccessComponent } from './payment-success/payment-success.component';
import { PaymentFailedComponent } from './payment-failed/payment-failed.component';
import { TestPageComponent } from './test-page/test-page.component';
import { UserTransactionsComponent } from './user-transactions/user-transactions.component';
import { MatTableModule } from '@angular/material';

const appRoutes: Routes = [
  {path: 'register', component : RegistrationComponent},
  {path: 'confirm/:username/:id', component : ConfirmComponent},
  {path: 'login', component : LoginComponent},
  {path: '', component : HomeComponent},
  {path: 'task/:taskId', component : TaskComponent},
  {path: 'testPayment', component : TaskComponent},
  {path: 'error', component : PaymentErrorComponent},
  {path: 'success', component : PaymentSuccessComponent},
  {path: 'failed', component : PaymentFailedComponent},
  {path: 'myTransactions', component : UserTransactionsComponent}
  ]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    ConfirmComponent,
    HomeComponent,
    LoginComponent,
    TaskComponent,
    PaymentErrorComponent,
    PaymentSuccessComponent,
    PaymentFailedComponent,
    TestPageComponent,
    UserTransactionsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatTableModule,
    MultiSelectAllModule,
    RouterModule.forRoot(
      appRoutes,
      {enableTracing : true}
    ),
    
  ],
  providers: [httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }

