import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/User';
import { JwtResponse } from './jwt-response';
import { AuthLoginInfo } from './login-info';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  

 // private loginUrl = 'https://localhost:8095/auth/login';
 // private signupUrl = 'https://localhost:8095/auth/signup';

  private loginUrl = 'http://localhost:8095/auth/login';
  private signupUrl = 'http://localhost:8095/auth/signup';
  constructor(private http: HttpClient) { }

  attemptAuth(credentials: AuthLoginInfo): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(this.loginUrl, credentials);
  }

  signUp(info: User): Observable<string> {
    return this.http.post<string>(this.signupUrl, info, httpOptions);
  }
}
