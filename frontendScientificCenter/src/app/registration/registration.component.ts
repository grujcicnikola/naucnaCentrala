import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegistrationService } from '../service/registrationService/registration.service';
import { FormFields } from '../model/FormFields';
import { ScientificAreaService } from '../service/scientificAreaService/scientific-area.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../model/User';
import { TokenStorageService } from '../auth/token-storage.service';
import { UserService } from '../service/userService/user.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = new FormFields();
  private formFields = [];
  private choosen_category = -1;
  processInstance : any;
  private enumValues = [];
  private tasks = [];
  private areas = [];
  private message = "You must select at least one scientific area!";
  private valid = true;
  someoneLogged : boolean = false;
  email : string = "";
  adminLogged : boolean = false;
  edditorLogged : boolean = false;
  recenzentLogged: boolean = false;
  roles: string[];
  loggedUser : User;
  constructor(private router: ActivatedRoute, private regService: RegistrationService, private areasService: ScientificAreaService,
    private userServ : UserService,private tokenStorage : TokenStorageService) { 
    //let x = regService.startProcess();
    this.regService.startProcess().subscribe(
      data =>{
        this.formFieldsDto = data;
        console.log(this.formFields);
        this.formFields = this.formFieldsDto.formFields;
        this.processInstance = this.formFieldsDto.processInstanceId;
        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
       
      },error =>{alert("Error")});
      this.areasService.getAll().subscribe(
        res =>{
          this.areas=res;
        }
      )
    
  }

  ngOnInit() {
    
    
  }

  
  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if(property !="scientificAreas")
        o.push({fieldId : property, fieldValue : value[property]});
      else{
        o.push({fieldId : property, areas : value[property]});
        if(value[property] !=""){
          this.valid=true;
        }else{
          this.valid = false;
          console.log("validacija "+this.valid);
        }
      }
    }
    
    console.log(o);
    if(this.valid==true){
      let x = this.regService.registerUser(o, this.formFieldsDto.taskId);

      x.subscribe(
        res => {
          console.log(res);
          
          alert("You registered successfully!")
          window.location.href = "https://localhost:4202";
        },
        err => {
          this.handleError(err)
        }
      );
    }
  }

  handleError(err: HttpErrorResponse) {
  
    if (err.status === 400) {
      alert('You did not fill all required data!');
    }else if(err.status === 406){
      alert('Username is already in use!');
    }
    else if(err.status === 409){
      alert('Email is already in use!');
    }
    else{
      alert('Error ocured!');
    }
    
  }
}
