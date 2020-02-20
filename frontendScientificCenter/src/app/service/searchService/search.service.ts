import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CombineQuery } from 'src/app/model/CombineQuery';
import { Paper } from 'src/app/model/Paper';
import { PaperResponse } from 'src/app/model/PaperResponse';
import { Recenzent } from 'src/app/model/Recenzent';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http:HttpClient) { }
  
  url ="https://localhost:8088/search"
  
  search(data: CombineQuery){
    return this.http.post<PaperResponse[]>(this.url+'/searchQuery',data);
   }

   moreLikeThis(idPaper: number) {
    return this.http.get<Recenzent[]>(this.url+'/moreLikeThis/'+idPaper);
  }

  geoPoint(idPaper: number) {
    return this.http.get<Recenzent[]>(this.url+'/geoPoint/'+idPaper);
  }
}
