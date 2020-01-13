import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from '../model/User';
import { AuthLoginInfo } from '../auth/login-info';
import { TokenStorageService } from '../auth/token-storage.service';
import { AuthService } from '../auth/auth.service';
import { UserService } from '../service/userService/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private email : string;
  private password : string;
  user : User;
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  loginInfo: AuthLoginInfo;
  constructor(private router: ActivatedRoute, private userServ : UserService,
    private authService: AuthService, private tokenStorage: TokenStorageService) { }

  ngOnInit() {
  }

  sign(){
    if(this.isBlank(this.email))
    {
      alert("You must enter the email!");
    }else if(this.isBlank(this.password))
    {
      alert("You must enter the password!");
    }else
    {
      
      this.userServ.getUserByEmail(this.email).subscribe(data =>{
       
        
        this.user = data as User;
        /*let temp = "notOk";

        for (let ur of this.user.roles){
          if(ur.id == 1)
             temp = "ok";
        }
        if(temp == "notOk"){
          alert("You dont have permission to sign in");
        }

        else*/
        if(this.user == undefined)
        {
          alert("Register first");
        }/*else if(this.user.password != this.password)
        {
          console.log(this.user.password);
          console.log(this.password);
          alert("Check password");
        }*/
        else{
          
          this.loginInfo = new AuthLoginInfo(
            this.email,
            this.password);
          
          console.log("Email " + this.loginInfo.email);
          console.log("Pass " + this.loginInfo.password);
          this.authService.attemptAuth(this.loginInfo).subscribe(
            data => {
              this.tokenStorage.saveToken(data.token);
              //this.tokenStorage.saveUsername(data.email);
              //this.tokenStorage.saveAuthorities(data.authorities);
      
              this.isLoginFailed = false;
              this.isLoggedIn = true;
              //this.roles = this.tokenStorage;
              alert("Succesful");
              window.location.href = "http://localhost:4200";
            },err =>{this.handleAuthError(err)}
          );

          
        
		}
	  },err =>{this.handleError(err)});
    }
    
  }

  isBlank(str) {
    return (!str || /^\s*$/.test(str));
  }

  handleError(err: HttpErrorResponse) {
  
    if (err.status === 404) {
      alert('User with given email and password doesn t exist!');
    }else if(err.status === 406) {
      alert('Given password is incorrect!');
    }else if(err.status === 401){
      alert('Unauthorized!');
    }
    
  }

  handleAuthError(err : HttpErrorResponse) {

    if(err.status === 403){
      alert("Check your email, you need to confirm registration first!");
    }else
    {
      alert("Probleem");
    }
  }
}
