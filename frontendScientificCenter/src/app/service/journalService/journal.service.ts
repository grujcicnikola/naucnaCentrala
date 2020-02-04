import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormFields } from 'src/app/model/FormFields';
import { Observable } from 'rxjs';
import { UserEditor } from 'src/app/model/UserEditor';
import { Journal } from 'src/app/model/Journal';

@Injectable({
  providedIn: 'root'
})
export class JournalService {
  
  
  url ="https://localhost:8088/"

  constructor(private http:HttpClient) { }
  
  startProcess(email: String){
    return this.http.get<FormFields>(this.url+'journal/create/'+email+'/')
   }

   confirmForm(id: string){
    return this.http.get<FormFields>(this.url+'registation/confirmForm/'.concat(id));
   }

  getEditors(procesInstanceId: string) {
    return this.http.get<UserEditor[]>(this.url+'journal/editors/'.concat(procesInstanceId));
  }

  getRecenzents(procesInstanceId: string) {
    return this.http.get<UserEditor[]>(this.url+'journal/recenzents/'.concat(procesInstanceId));
  }

  getTasks(processInstance : string){

    return this.http.get(this.url+'journal/getNextTaskForm/'.concat(processInstance)) as Observable<any>
  }

  registerUser(user, taskId) {
    return this.http.post(this.url+"journal/userInput/".concat(taskId), user) as Observable<any>;
  }

  registerEditorsAndRecenzents(user, taskId) {
    return this.http.post(this.url+"journal/editorsAndRecenzent/".concat(taskId), user) as Observable<any>;
  }

  confirmUser(username:string,user, taskId) {
    return this.http.post(this.url+"registation/confirmUserInput/"+username+'/'.concat(taskId), user) as Observable<any>;
  }

  getAll() {
    return this.http.get<Journal[]>(this.url+'journal/journals') as Observable<any>
  }

}
