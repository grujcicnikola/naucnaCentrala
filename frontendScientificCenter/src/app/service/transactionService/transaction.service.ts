import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { create } from 'domain';
import { Transaction } from 'src/app/model/Transaction';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

    constructor(private http : HttpClient) { }
    
    url ="https://localhost:8088/"
    
    create(transaction : Transaction){
      return this.http.post<Transaction>(this.url+'transaction/create',transaction);
    }

    get(email : any):Observable<any>{
      return this.http.get(this.url+'transaction/get/'+email);
    }
    
}
