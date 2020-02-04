import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MethodOfPayment } from 'src/app/model/MethodOfPayment';

@Injectable({
  providedIn: 'root'
})
export class MethodOfPaymentService {

  constructor(private http:HttpClient) { }
  url ="https://localhost:8088/"
  getAll(){
    return this.http.get<MethodOfPayment[]>(this.url+'methodOfPayment/getAll')
   }
  }
