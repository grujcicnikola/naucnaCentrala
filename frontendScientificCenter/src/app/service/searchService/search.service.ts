import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CombineQuery } from 'src/app/model/CombineQuery';
import { Paper } from 'src/app/model/Paper';
import { PaperResponse } from 'src/app/model/PaperResponse';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http:HttpClient) { }
  
  url ="https://localhost:8088/"
  
  search(data: CombineQuery){
    return this.http.post<PaperResponse[]>(this.url+'search/searchQuery',data);
   }
}
