import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegistrationService } from '../service/registrationService/registration.service';
import { FormFields } from '../model/FormFields';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.css']
})
export class ConfirmComponent implements OnInit {
  private repeated_password = "";
  private categories = [];
  private formFieldsDto = new FormFields();
  private formFields = [];
  private choosen_category = -1;
  processInstance : any;
  private enumValues = [];
  private tasks = [];
  private valid = true;
  private processId : string;
  private username: string;
  constructor(private router: ActivatedRoute, private regService: RegistrationService) { 
    //let x = regService.startProcess();
    this.processId = this.router.snapshot.params.id;
    this.username = this.router.snapshot.params.username;
    this.regService.confirmForm(this.processId).subscribe(
        data =>{
          this.formFieldsDto = data;
          this.formFields = this.formFieldsDto.formFields;
          this.processInstance = this.formFieldsDto.processInstanceId;
          this.formFields.forEach( (field) =>{
            
            if( field.type.name=='enum'){
              this.enumValues = Object.keys(field.type.values);
            }
          });
         
        },error =>{alert("Error")});
      
    
  }

  ngOnInit() {
  }

  
  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      
      o.push({fieldId : property, fieldValue : value[property]});
    }
    
    console.log(o);
    let x = this.regService.confirmUser(this.username,o, this.formFieldsDto.taskId);

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

  handleError(err: HttpErrorResponse) {
  
    if (err.status === 400) {
      alert('You did not fill all required data!');
    }else{
      alert('Error ocured!');
    }
    
  }
}

