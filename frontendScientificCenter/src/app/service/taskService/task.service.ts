import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormFields } from 'src/app/model/FormFields';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  

  constructor(private http : HttpClient) { }
  url ="https://localhost:8088/"

  taskForm(task : String){
    return this.http.get<FormFields>(this.url+'task/getTaskForm/'+task);
  }

  registerUser(user, taskId) {
    return this.http.post(this.url+"task/userInput/".concat(taskId), user) as Observable<any>;
  }
}
