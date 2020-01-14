import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormFields } from 'src/app/model/FormFields';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  

  constructor(private http : HttpClient) { }

  taskForm(task : String){
    return this.http.get<FormFields>('http://localhost:8095/task/getTaskForm/'+task);
  }

  registerUser(user, taskId) {
    return this.http.post("http://localhost:8095/task/userInput/".concat(taskId), user) as Observable<any>;
  }
}
