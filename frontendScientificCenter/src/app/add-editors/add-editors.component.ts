import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { ScientificAreaService } from '../service/scientificAreaService/scientific-area.service';
import { RegistrationService } from '../service/registrationService/registration.service';
import { FormFields } from '../model/FormFields';
import { JournalService } from '../service/journalService/journal.service';

@Component({
  selector: 'app-add-editors',
  templateUrl: './add-editors.component.html',
  styleUrls: ['./add-editors.component.css']
})
export class AddEditorsComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = new FormFields();
  private formFields = [];
  private choosen_category = -1;
  processInstance : any;
  private enumValues = [];
  private tasks = [];
  private areas = [];
  private message = "You must select at least one editor!";
  private message1 = "You must select at least two recenzents!";
  private valid = true;
  private valid1 = true;
  private procesInstanceId : string;
  private editors = [];
  private recenzents =[];
  constructor(private router: ActivatedRoute, private journalService: JournalService, private areasService: ScientificAreaService) { 
    //let x = regService.startProcess();
    this.procesInstanceId = this.router.snapshot.params.id;
    this.journalService.getTasks(this.procesInstanceId).subscribe(
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
      this.journalService.getEditors(this.procesInstanceId).subscribe(
        res =>{
          this.editors=res;
        }
      )
      this.journalService.getRecenzents(this.procesInstanceId).subscribe(
        res =>{
          this.recenzents=res;
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
      if(property =="editors"){
        o.push({fieldId : property, fieldValue : value[property]});
        if(value[property] !=""){
          this.valid=true;
        }else{
          this.valid = false;
          console.log("validacija "+this.valid);
        }
      }else{
        o.push({fieldId : property, areas : value[property]});
        if(value[property].length>=2){
          this.valid1=true;
        }else{
          this.valid1 = false;
          console.log("validacija "+this.valid1);
        }
      }
    }
    console.log("validacijaK "+this.valid);
    console.log("validacijaK "+this.valid1);
    console.log(o);
    if(this.valid==true && this.valid1==true){
      let x = this.journalService.registerEditorsAndRecenzents(o, this.formFieldsDto.taskId);

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

