import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-payment-failed',
  templateUrl: './payment-failed.component.html',
  styleUrls: ['./payment-failed.component.css']
})
export class PaymentFailedComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  home(){
    
    window.location.href="https://localhost:4202";
    
    
  }

}
