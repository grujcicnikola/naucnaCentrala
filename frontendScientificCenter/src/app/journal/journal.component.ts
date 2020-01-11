import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegistrationService } from '../service/registrationService/registration.service';
import { FormFields } from '../model/FormFields';
import { ScientificAreaService } from '../service/scientificAreaService/scientific-area.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MethodOfPaymentService } from '../service/methodOfPayment/method-of-payment.service';
import { JournalService } from '../service/journalService/journal.service';

@Component({
  selector: 'app-journal',
  templateUrl: './journal.component.html',
  styleUrls: ['./journal.component.css']
})
export class JournalComponent implements OnInit {
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
  private methods =[];
  constructor(private router: ActivatedRoute, private journalService: JournalService,
    private methodsService: MethodOfPaymentService, private areasService: ScientificAreaService) { 
    //let x = regService.startProcess();
    this.journalService.startProcess().subscribe(
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
      this.areasService.getAll().subscribe(
        res =>{
          this.areas=res;
        }
      )
      this.methodsService.getAll().subscribe(
        res =>{
          this.methods=res;
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
      let x = this.journalService.registerUser(o, this.formFieldsDto.taskId);

      x.subscribe(
        res => {
          console.log(res);
          
          alert("You registered successfully!")
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
    }else{
      alert('Error ocured!');
    }
    
  }
}
