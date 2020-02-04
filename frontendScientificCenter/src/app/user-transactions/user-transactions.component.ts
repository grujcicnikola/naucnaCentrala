import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../service/transactionService/transaction.service';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-user-transactions',
  templateUrl: './user-transactions.component.html',
  styleUrls: ['./user-transactions.component.css']
})
export class UserTransactionsComponent implements OnInit {

  trans = [];
  displayedColumns: string[] = ['Journal', 'Amount', 'Status'];

  email : string = "";
  constructor(private ts : TransactionService,private tokenStorage : TokenStorageService) { 
    if (this.tokenStorage.getToken()) {

      let jwt = this.tokenStorage.getToken();
      console.log("Tokeen: " + jwt);
      let jwtData = jwt.split('.')[1];
      let decodedJwtJsonData = window.atob(jwtData);
      let decodedJwtData = JSON.parse(decodedJwtJsonData);
      this.email = decodedJwtData.sub;

      ts.get(this.email).subscribe(res => {
        this.trans = res;
        console.log("trans " + this.trans);
        console.log("res " + res);
      })
    }
    
    
  }

  ngOnInit() {
  }

  home(){
    
    window.location.href="https://localhost:4202";
    
    
  }

}
