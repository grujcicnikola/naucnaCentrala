import { Component, OnInit } from '@angular/core';
import { PaymentData } from '../model/PaymentData';
import { ActivatedRoute } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-test-page',
  templateUrl: './test-page.component.html',
  styleUrls: ['./test-page.component.css']
})
export class TestPageComponent implements OnInit {

  payment : PaymentData = new PaymentData();
  errorMessage = '';
  
  constructor(private router: ActivatedRoute) { 
    
  }

  ngOnInit() {
  }
  

  
  sign(){
   // this.paymentService.createPayment(this.payment).subscribe(
   //     data =>{
   //      alert("Payment successfully registered!");
   //     },error =>{this.handleAuthError(error)});
   console.log(this.payment.merchantEmail);
   console.log(this.payment.amount);

   if(this.isBlank(this.payment.merchantEmail))
   {
     alert("Fill email field");
   }else if (this.isBlank(this.payment.amount))
   {
     alert("Fill amount field");
   }else
   {
    window.location.href = "https://localhost:1234/"+ this.payment.merchantEmail + "/" + this.payment.amount;
   }

  }    
  

  handleAuthError(err : HttpErrorResponse) {

    if(err.status === 400){
      alert("This data is not valid!");
    }else
    {
      alert("Problem");
    }
  }

  isBlank(str) {
    return (!str || /^\s*$/.test(str));
  }

  
}