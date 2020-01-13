import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormFields } from 'src/app/model/FormFields';
import { Observable } from 'rxjs';
import { UserEditor } from 'src/app/model/UserEditor';

@Injectable({
  providedIn: 'root'
})
export class JournalService {
  
  

  constructor(private http:HttpClient) { }
  
  startProcess(){
    return this.http.get<FormFields>('http://localhost:8095/journal/create')
   }

   confirmForm(id: string){
    return this.http.get<FormFields>('http://localhost:8095/registation/confirmForm/'.concat(id));
   }

  getEditors(procesInstanceId: string) {
    return this.http.get<UserEditor[]>('http://localhost:8095/journal/editors/'.concat(procesInstanceId));
  }

  getRecenzents(procesInstanceId: string) {
    return this.http.get<UserEditor[]>('http://localhost:8095/journal/recenzents/'.concat(procesInstanceId));
  }

  getTasks(processInstance : string){

    return this.http.get('http://localhost:8095/journal/getNextTaskForm/'.concat(processInstance)) as Observable<any>
  }

  claimTask(taskId){
    return this.http.post('http://localhost:8080/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeTask(taskId){
    return this.http.post('http://localhost:8080/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  }

  registerUser(user, taskId) {
    return this.http.post("http://localhost:8095/journal/userInput/".concat(taskId), user) as Observable<any>;
  }

  registerEditorsAndRecenzents(user, taskId) {
    return this.http.post("http://localhost:8095/journal/editorsAndRecenzent/".concat(taskId), user) as Observable<any>;
  }

  confirmUser(username:string,user, taskId) {
    return this.http.post("http://localhost:8095/registation/confirmUserInput/"+username+'/'.concat(taskId), user) as Observable<any>;
  }
}
