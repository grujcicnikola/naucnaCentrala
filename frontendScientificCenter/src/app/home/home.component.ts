import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../service/userService/user.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { User } from '../model/User';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  messages: any;
  clicked: boolean=false;
  myArray: any;
  someoneLogged : boolean = false;
  email : string = "";
  adminLogged : boolean = false;
  edditorLogged : boolean = false;
  roles: string[];
  loggedUser : User;
  recenzentLogged: boolean = false;
  tasks: any;
  
  constructor(private router: ActivatedRoute, private tokenStorage : TokenStorageService,
    private userServ : UserService) { }


  ngOnInit() {
    if (this.tokenStorage.getToken()) {
      this.someoneLogged = true;
      
      let jwt = this.tokenStorage.getToken();
      console.log("Tokeen: " + jwt);
      let jwtData = jwt.split('.')[1];
      let decodedJwtJsonData = window.atob(jwtData);
      let decodedJwtData = JSON.parse(decodedJwtJsonData);
      this.email = decodedJwtData.sub;

      //console.log('jwtData: ' + jwtData);
      //console.log('decodedJwtJsonData: ' + decodedJwtJsonData);
      //console.log('decodedJwtData: ' + decodedJwtData);
      //console.log('User: ' + this.email);

      this.userServ.getUserByEmail(this.email).subscribe(data =>{
        this.loggedUser = data as User;

        this.loggedUser.roles.forEach(element =>{
          console.log("Uloga: " + element.name);
          if(element.name === "ROLE_ADMIN")
          {
            this.adminLogged = true;
          }else if(element.name=== "ROLE_EDITOR"){
            this.edditorLogged = true;
          }else if(element.name === "ROLE_RECENZENT"){
            this.recenzentLogged = true;
          }
        });
      });
    }
  }

  logout(){
    this.tokenStorage.signOut();
    this.someoneLogged = false;
    this.userServ.logout(this.email).subscribe(data =>{
      window.location.href="http://localhost:4200";
    });
    
  }

  handleAuthError(err: HttpErrorResponse) {
  
    if(err.status === 401){
      alert('Error unauthorized!');
    }else if(err.status === 500){
      window.location.href = "http://localhost:4200";
    }
    else
    {
      alert('Greska se desila');
    }
    
  }

  getTasks(){
    this.userServ.getUsersTasks(this.email).subscribe(data =>{
      console.log(data);
      this.tasks = data;
    })

  }

  startTask(task){
    console.log(task);
    window.location.href="http://localhost:4200/task/".concat(task);
  }
}
