import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http : HttpClient) { }

  logout(email : String){
    return this.http.get('http://localhost:8095/auth/logout/'+email+'/');
  }
  
  getUserByEmail(email : String){
    return this.http.get('http://localhost:8095/user/email/'+email+'/');
  }

  //get/tasks'
  getUsersTasks(email : String){
    return this.http.get('http://localhost:8095/user/get/tasks/'+email+'/');
  }
}
