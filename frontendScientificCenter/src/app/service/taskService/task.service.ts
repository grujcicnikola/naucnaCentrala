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

  findLongitudeAndLatitude(adresa: String){
    console.log("adresa: "+adresa);
    return this.http.get('https://nominatim.openstreetmap.org/search?q=%20"+'+adresa+'+"%20&format=json');
  }

  registerCoauthors(user, taskId,lon,lat) {
    return this.http.post(this.url+"task/coauthors/".concat(taskId)+"/"+lon+"/"+lat, user) as Observable<any>;
  }
}
