import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormFields } from 'src/app/model/FormFields';
import { Journal } from 'src/app/model/Journal';
import { Observable } from 'rxjs/internal/Observable';
import { PDF } from 'src/app/model/PDF';
import { PDFURL } from 'src/app/model/PDFURL';

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

   choosingReviewersFilteredByScientificArea(id: string){
    return this.http.get<FormFields>(this.url+'/choosingReviewersFilteredByScientificArea/'.concat(id));
   }
   getCommentsFromRecenzentsToEditor(id: string){
    return this.http.get<Comment[]>(this.url+'/commentsRecenzentsToEditor/'+id);
   }

   postFile( fileToUpload: File) {
    const formData: FormData = new FormData();  
    formData.append("File", fileToUpload);
    return this.http.post(this.url + '/uploadPDF/', formData,{ responseType: 'text'});
      
  }

  getPDF(link: String) {
    console.log("servis"+link);
    return this.http.post<PDF>(this.url + '/getPDF',new PDFURL(link));
  }

  getURI(id: number){
    return this.http.get<PDFURL>(this.url+'/paper/'+id);
   }

  downloadFile(link: String): Observable<Blob>{
    const headers = new HttpHeaders({ responseType : 'blob'});

		return this.http.post<Blob>(this.url + '/download',new PDFURL(link), {headers: headers, responseType: 'blob' as 'json'});
  }

  downloadPDF(idPaper: number): Observable<Blob>{
    const headers = new HttpHeaders({ responseType : 'blob'});
    console.log("downloadFILE"+idPaper);
		return this.http.post<Blob>(this.url + '/downloadPDF/'+idPaper, {headers: headers, responseType: 'blob' as 'json'});
  }
}