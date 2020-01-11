import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MethodOfPayment } from 'src/app/model/MethodOfPayment';

@Injectable({
  providedIn: 'root'
})
export class MethodOfPaymentService {

  constructor(private http:HttpClient) { }
  
  getAll(){
    return this.http.get<MethodOfPayment[]>('http://localhost:8095/methodOfPayment/getAll')
   }
  }
