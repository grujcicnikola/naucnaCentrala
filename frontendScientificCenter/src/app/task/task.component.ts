import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { FormFields } from '../model/FormFields';
import { RegistrationService } from '../service/registrationService/registration.service';
import { ScientificAreaService } from '../service/scientificAreaService/scientific-area.service';
import { TaskService } from '../service/taskService/task.service';
import { MethodOfPaymentService } from '../service/methodOfPayment/method-of-payment.service';
import { JournalService } from '../service/journalService/journal.service';

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
  private helpVariableForWay : any;
  private editors = [];
  private recenzents =[];
  private valueSA ="";
  private ways : any;
  private scarea11: any;

  constructor(private router: ActivatedRoute, private regService: RegistrationService, 
    private areasService: ScientificAreaService, private taskService: TaskService,
     private methodsService: MethodOfPaymentService, private journalService: JournalService) { 
    //let x = regService.startProcess();
    this.taskId = this.router.snapshot.params.taskId;
    this.taskService.taskForm(this.taskId).subscribe(
        data =>{
          this.formFieldsDto = data;
          console.log(data);
          this.formFields = this.formFieldsDto.formFields;
          this.processInstance = this.formFieldsDto.processInstanceId;
          this.areasService.getAll().subscribe(
            res =>{
              this.areas=res;
              this.formFields.forEach( (field) =>{
            
                if( field.type.name=='enum'){
                  this.enumValues = Object.keys(field.type.values);
                }
              });
              this.formFields.forEach( (field) =>{
            
                if( field.id=='scientificAreas'){
                  var splitted = field.value.value.split(","); 
                  console.log(splitted); 
                  var niz =[];
                  this.areas.forEach(area=>{
                    var i =0;
                    splitted.forEach(element => {
                      console.log(element +" "+ area.name); 
                        if(element ==area.name){
                          niz.push(area);
                        }
                    });
                    
                  })
                  console.log(niz);
                  this.scarea11=niz;
                  //this.onChange(niz[0]);
                 
                }
              });
              

            }
            
          )
          this.journalService.getEditors(this.processInstance).subscribe(
            res =>{
              this.editors=res;
            }
          )
          this.journalService.getRecenzents(this.processInstance).subscribe(
            res =>{
              this.recenzents=res;
            }
          )  
          
          this.methodsService.getAll().subscribe(
            res =>{
              console.log("methode "+res);
              this.methods=res;
              this.formFields.forEach( (field) =>{
            
                if( field.id=='wayOfPaying1' || (field.id=='wayOfPaying' && field.value.value!="")){
                  //this.enumValues = Object.keys(field.type.values);
                  res.forEach(element=>{
                    if(element.id==field.value.value){
                      console.log("------ "+field.value.value);
                      this.helpVariableForWay= field.value.value;
                      field.value.value=element.name;
                      console.log("-----+ "+field.value.value);
                      let element1 =<any> document.getElementById("wayOfPaying");
                      console.log(element1);
                      element1.values=this.helpVariableForWay;
                      this.onChange1(this.helpVariableForWay);
                      
                      //((document.getElementById("wayOfPaying")) as HTMLElement).nodeValue = this.helpVariableForWay; ;
                    }
                  })
                }
                
              });
            }
          )
          
         
        },error =>{alert("Error")});
      
    
  }

  ngOnInit() {
  }

  
  onSubmit(value, form){
    let o = new Array();
    for (var property in value) {
      console.log(property);
      console.log(value[property]);
      if(property !="scientificAreas" && property !="recenzents"){
        if(property =="wayOfPaying1"){
          o.push({fieldId : property, fieldValue : this.helpVariableForWay});
        
        
        }else{
          if(property!="scientificAreas1"){
            o.push({fieldId : property, fieldValue : value[property]});
          
            if(value[property] !="" || property=="activateJournal" || property=="isOpenAccess"){
              this.valid=true;
            }else{
              console.log(property)
              this.valid = false;
              console.log("validacija "+this.valid);
            }
          }
          
        }
      }
      else{
        if(property !="scientificAreas"){
          o.push({fieldId : property, areas : value[property]});
          //o.push({fieldId : property, fieldValue : value[property]});
        }else{
        ///o.push({fieldId : property, areas : value[property]});
          this.valueSA="";
          this.areas.forEach(area=>{
                var i =0;
                value[property].forEach(element => {
                    if(element ==area.id){
                      if(i==0){
                        this.valueSA +=area.name;
                      }else{
                        this.valueSA +=",";
                        this.valueSA +=area.name;
                      }
                    }
                    i++;
                });
              })
              //this.valueSA +=value[property][i];  
            
          
          o.push({fieldId : property, areas : value[property], fieldValue : this.valueSA});
          console.log("******"+this.valueSA);
          if(value[property] !=""){
            this.valid=true;
          }else{
            this.valid = false;
            console.log("validacija "+this.valid);
          }
          if(value[property].length>=2){
            this.valid=true;
          }else{
            this.valid = false;
          }
          if(value[property] !=""){
            this.valid=true;
          }else{
            this.valid = false;
            console.log("validacija "+this.valid);
          }
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
          window.location.href = "http://localhost:4200";
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
    }else if (err.status === 226) {
      alert('User with same email already exists!');
    }
    
    else{
      alert('Error ocured!');
    }
    
  }
  onChange(newValue) {
    let element1 =<any> document.getElementById("scientificAreas");
    console.log(element1);
    console.log(newValue);
    this.scarea11 = newValue;  // don't forget to update the model here
    // ... do other stuff here ...
}

onChange1(newValue) {
  
  console.log(newValue);
  this.ways = newValue;  // don't forget to update the model here
  // ... do other stuff here ...
}
}

