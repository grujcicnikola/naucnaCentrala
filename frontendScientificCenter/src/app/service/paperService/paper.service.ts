import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormFields } from 'src/app/model/FormFields';
import { Journal } from 'src/app/model/Journal';

@Injectable({
  providedIn: 'root'
})
export class PaperService {

  
  url ="https://localhost:8088/paper"
  constructor(private http:HttpClient) { }
  
  startProcess(email: String, journal: Journal){
    return this.http.post<FormFields>(this.url+'/create/'+email,journal);
   }

   confirmForm(id: string){
    return this.http.get<FormFields>(this.url+'/confirmForm/'.concat(id));
   }

   postFile( fileToUpload: File) {
    const formData: FormData = new FormData();  
    formData.append("File", fileToUpload);
    return this.http.post(this.url + '/uploadPDF/', formData,{ responseType: 'text'});
      
  }

}
