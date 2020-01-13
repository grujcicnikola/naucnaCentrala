import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private username : string;
  private password : string;
  constructor(private router: ActivatedRoute) { }

  ngOnInit() {
  }

  sign(){
    
  }
}
