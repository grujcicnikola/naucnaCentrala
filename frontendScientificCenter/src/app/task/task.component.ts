import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { FormFields } from '../model/FormFields';
import { RegistrationService } from '../service/registrationService/registration.service';
import { ScientificAreaService } from '../service/scientificAreaService/scientific-area.service';
import { TaskService } from '../service/taskService/task.service';
import { MethodOfPaymentService } from '../service/methodOfPayment/method-of-payment.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

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
  private taskId: string;
  private obj : any;
  methods: any;
  constructor(private router: ActivatedRoute, private regService: RegistrationService, 
    private areasService: ScientificAreaService, private taskService: TaskService, private methodsService: MethodOfPaymentService) { 
    //let x = regService.startProcess();
    this.taskId = this.router.snapshot.params.taskId;
    this.taskService.taskForm(this.taskId).subscribe(
        data =>{
          this.formFieldsDto = data;
          console.log(data);
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
    console.log("validno"+this.valid);
    if(this.valid==true){
      let x = this.taskService.registerUser(o, this.formFieldsDto.taskId);

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

