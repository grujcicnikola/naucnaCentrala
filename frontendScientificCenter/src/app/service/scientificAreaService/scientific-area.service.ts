import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ScientificArea } from 'src/app/model/ScientificArea';

@Injectable({
  providedIn: 'root'
})
export class ScientificAreaService {

  constructor(private http:HttpClient) { }
  url ="https://localhost:8088/"
  getAll(){
    return this.http.get<ScientificArea[]>(this.url+'scientificArea/getAll')
   }
}
