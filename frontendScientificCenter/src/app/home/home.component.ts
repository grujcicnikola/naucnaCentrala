import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../service/userService/user.service';
import { TokenStorageService } from '../auth/token-storage.service';
import { User } from '../model/User';
import { HttpErrorResponse } from '@angular/common/http';
import { RegistrationService } from '../service/registrationService/registration.service';
import { JournalService } from '../service/journalService/journal.service';
import { Journal } from '../model/Journal';
import { TransactionService } from '../service/transactionService/transaction.service';
import { Transaction } from '../model/Transaction';
import { PaperService } from '../service/paperService/paper.service';

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
  editorLogged : boolean = false;
  roles: string[];
  loggedUser : User;
  recenzentLogged: boolean = false;
  userLogged : boolean = false;
  authorLogged : boolean = false;
  tasks: any;
  journals: Journal[];
  
  constructor(private router: ActivatedRoute, private tokenStorage : TokenStorageService,
    private userServ : UserService, private regService: RegistrationService,private journalService: JournalService,
    private transServ: TransactionService, private paperService: PaperService) { }


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
            this.editorLogged = true;
          }else if(element.name === "ROLE_RECENZENT"){
            this.recenzentLogged = true;
          }else if(element.name === "ROLE_USER"){
            this.userLogged = true;
          }else if(element.name === "ROLE_AUTHOR"){
            this.authorLogged = true;
          }
        });
      });
    }
  }

  logout(){
    this.tokenStorage.signOut();
    this.someoneLogged = false;
    this.userServ.logout(this.email).subscribe(data =>{
      window.location.href="https://localhost:4202";
    });
    
  }

  handleAuthError(err: HttpErrorResponse) {
  
    if(err.status === 401){
      alert('Error unauthorized!');
    }else if(err.status === 500){
      window.location.href = "https://localhost:4202";
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
    window.location.href="https://localhost:4202/task/".concat(task);
  }

  startRegister(){
    window.location.href="https://localhost:4202/register";

  }

  startLogin(){
    window.location.href="https://localhost:4202/login";
  }

  userTransactions(){
    window.location.href="https://localhost:4202/myTransactions";
  }
  startJournal(){
    this.journalService.startProcess(this.email).subscribe(
      data =>{
        window.location.href="https://localhost:4202/task/"+data.taskId;
      },error =>{alert("Error")});
  }

  seeList(){
    console.log("ivana");
    this.journalService.getAll().subscribe(
      data=>{
        this.journals=data;

        this.journals.forEach(element =>{
          console.log("SUBS"+element.subscriptions);
          if(element.subscriptions != null)
          {
            console.log("Element " + element.title + " , sub: " + element.subscriptions.length);
            
            element.canSubscribe = true;
            element.canUnsubscribe = false;
            element.subscriptions.forEach(element2 =>{
              console.log("ACTIVE STATUS" + element2.active);
              if(element2.userEmail == this.email && element2.active == true)
              { 
                element.canSubscribe = false;
                element.canUnsubscribe = true;
              }else if(element2.userEmail == this.email && element2.active == false)
              {
               
                element.canSubscribe = true;
                element.canUnsubscribe = false;
              }
            });
          }else
          {
            element.canSubscribe = true;
            element.canUnsubscribe = false;
          }
           
        });
        console.log(this.journals);
      }
    )
  }

  buyJournal(journal: Journal){
    let transaction = new Transaction();
    transaction.buyerEmail = this.loggedUser.email;
    transaction.amount =journal.price;
    transaction.journalId=journal.id;
    transaction.merchantIssn=journal.issn;
    
    this.transServ.create(transaction).subscribe(
      data=>{
        alert("Success "+data.orderId);
        window.location.href = "https://localhost:1234/createPayment/"+data.orderId;
      },error =>{this.handleError(error)}
      );
    
    //console.log("kupi casopis sa idijem "+journal.id);
    //window.location.href = "https://localhost:1234/journal/"+journal.issn + "/" + journal.price;
  }

  submitPaper(journal: Journal){
    this.paperService.startProcess(this.email, journal).subscribe(
      data =>{
        window.location.href="https://localhost:4202/task/"+data.taskId;
      },error =>{alert("Error")});
  }

  subscribe(journal : Journal){
    
    window.location.href = "https://localhost:1234/subscription/"+journal.issn + "/" + this.email;
  }

  unsubscribe(journal : Journal){

    window.location.href= "https://localhost:1234/cancelSub/"+journal.issn + "/" + this.email;
  }

  handleError(err: HttpErrorResponse) {
    
    if(err.status === 503){
      alert('This magazine does not have any available payment methods!');
    }else{
      alert("Error");
    }
    
  }

}
