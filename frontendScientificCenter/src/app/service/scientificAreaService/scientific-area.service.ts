import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ScientificArea } from 'src/app/model/ScientificArea';

@Injectable({
  providedIn: 'root'
})
export class ScientificAreaService {

  constructor(private http:HttpClient) { }
  
  getAll(){
    return this.http.get<ScientificArea[]>('http://localhost:8095/scientificArea/getAll')
   }
}
