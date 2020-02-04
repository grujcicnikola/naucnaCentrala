import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormFields } from 'src/app/model/FormFields';
import { Task } from 'src/app/model/Task';
import { Form } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http:HttpClient) { }

  url ="https://localhost:8088/"

  startProcess(){
    return this.http.get<FormFields>(this.url+'registation/register');
   }

   confirmForm(id: string){
    return this.http.get<FormFields>(this.url+'registation/confirmForm/'.concat(id));
   }

 registerUser(user, taskId) {
    return this.http.post(this.url+"registation/userInput/".concat(taskId), user) as Observable<any>;
  }

  confirmUser(username:string,user, taskId) {
    return this.http.post(this.url+"registation/confirmUserInput/"+username+'/'.concat(taskId), user) as Observable<any>;
  }
}
