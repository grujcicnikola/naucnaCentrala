import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormFields } from 'src/app/model/FormFields';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http:HttpClient) { }
  
  startProcess(){
    return this.http.get<FormFields>('http://localhost:8095/registation/register')
   }

  getTasks(processInstance : string){

    return this.http.get('http://localhost:8080/welcome/get/tasks/'.concat(processInstance)) as Observable<any>
  }

  claimTask(taskId){
    return this.http.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeTask(taskId){
    return this.http.post('http://localhost:8080/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  }

  registerUser(user, taskId) {
    return this.http.post("http://localhost:8095/registation/userInput/".concat(taskId), user) as Observable<any>;
  }
}
