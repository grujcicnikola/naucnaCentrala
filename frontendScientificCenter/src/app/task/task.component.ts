import { Component, OnInit } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { FormFields } from '../model/FormFields';
import { RegistrationService } from '../service/registrationService/registration.service';
import { ScientificAreaService } from '../service/scientificAreaService/scientific-area.service';
import { TaskService } from '../service/taskService/task.service';
import { MethodOfPaymentService } from '../service/methodOfPayment/method-of-payment.service';
import { JournalService } from '../service/journalService/journal.service';
import { ScientificArea } from '../model/ScientificArea';
import { element } from 'protractor';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { PaperService } from '../service/paperService/paper.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { PDF } from '../model/PDF';

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
  private areas : ScientificArea[];
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
  private review = false;
  fileUrl: string;
  fileToUpload: File;
  hasPDF : boolean;
  selectedItems = [];
  dropdownList = [];
  enumLabels = [];
  enumNames = [];
  enumSingleSelectValues = {};
  dropdownSettings: IDropdownSettings = {};
 /* private scarea11: any;
  private sss: any;*/

  constructor(private router: ActivatedRoute, private regService: RegistrationService, 
    private areasService: ScientificAreaService, private taskService: TaskService,
     private methodsService: MethodOfPaymentService, private journalService: JournalService,
      private paperService: PaperService, private sanitizer: DomSanitizer) { 
    //let x = regService.startProcess();
    this.taskId = this.router.snapshot.params.taskId;
    this.taskService.taskForm(this.taskId).subscribe(
        data =>{
          this.formFieldsDto = data;
          console.log(data);
          this.formFields = this.formFieldsDto.formFields;
          this.fieldsFormat();
          this.processInstance = this.formFieldsDto.processInstanceId;
          this.areasService.getAll().subscribe(
            res =>{
              this.areas=res;
              this.formFields.forEach( (field) =>{
            
                /*if( field.type.name=='enum'){
                  this.enumValues = Object.keys(field.type.values);
                }*/
                if(field.id=="isOpenAccess1"){
                  this.review = true;
                  let element1 =<any> document.getElementById("name1");
                  let element2 =<any> document.getElementById("issnNumber1");
                  let element3 =<any> document.getElementById("scientificAreas1");
                  let element4 =<any> document.getElementById("wayOfPaying1");
                  //let element5 =<any> document.getElementById("isOpenAccess1");
                  element1.readOnly=true;
                  element2.readOnly=true;
                  element3.readOnly=true;
                  element4.readOnly=true;
                  //element5.readOnly=true;
                  
                }
                
              });
              /*this.formFields.forEach( (field) =>{
            
                if( field.id=='scientificAreas'){
                  let element11 =<any> document.getElementById("scientificAreas");
                      console.log(element11);
                      console.log(this.areas);
                      let element112 =<any> document.getElementById("serviceList");
                      console.log(element112);
                      console.log(this.areas);
                  var splitted = field.value.value.split(","); 
                  console.log(splitted); 
                  var niz =[];
                  this.areas.forEach(area=>{
                    var i =0;
                    splitted.forEach(element => {
                      //console.log(element +" "+ area.name); 
                        if(element ==area.name){
                          niz.push(area);
                        }
                    });
                    
                  })
                  console.log(niz);
                  this.scarea11=["1"]
                  this.sss.push(this.areas[0]);
                  let element1 =<any> document.getElementById("scientificAreas");
                      console.log(element1);
                      //element1.values=this.helpVariableForWay;
                      //this.onChange1(this.helpVariableForWay);
                  //this.onChange(niz[0]);
                 
                }
              });*/
              

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
                      if(element1!=null){
                        element1.values=this.helpVariableForWay;
                        this.onChange1(this.helpVariableForWay);
                      }
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

    this.dropdownSettings = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: true
    };
    this.hasPDF = false;
    
  
    
  
 

}
  
  onSubmit(value, form){
    this.valid = true;
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
              
              if(property=="editors"){
                this.message = "You must select at least one editor!"
              }else 
              this.valid = false;
              console.log("validacija "+this.valid);
            }
          }
          
        }
      }
      else{
        if(property !="scientificAreas"){
          o.push({fieldId : property, areas : value[property]});
          if(property=="recenzents"){
            if(value[property].length<2){
              this.message = "You must select at least two recenzents!"
              this.valid = false;
            }
          }
      
          //o.push({fieldId : property, fieldValue : value[property]});
        }else{
        ///o.push({fieldId : property, areas : value[property]});
          if(value[property].length<2){
            this.message = "You must select at least one area!"
            this.valid = false;
          }else{
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
            
            }
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
      
      if(this.hasPDF){
            this.paperService.postFile(this.fileToUpload).subscribe(data=>{
              o.push({fieldId : "pdf", fieldValue : data});
              console.log("konacno"+o);
              let x = this.taskService.registerUser(o, this.formFieldsDto.taskId);
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
            });
          
      
        
      }else{
        let x = this.taskService.registerUser(o, this.formFieldsDto.taskId);
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
    /*onChange(newValue) {
      let element1 =<any> document.getElementById("scientificAreas");
      console.log(element1);
      console.log(newValue);
      this.scarea11 = newValue;  // don't forget to update the model here
      // ... do other stuff here ...
  }*/

  onChange1(newValue) {
    
    console.log(newValue);
    this.ways = newValue;  // don't forget to update the model here
    // ... do other stuff here ...
  }


  function ($scope) {
      
      $scope.selection = [];
      
    /* $scope.toggle = function (idx) {
          var pos = $scope.selection.indexOf(idx);
          if (pos == -1) {
              $scope.selection.push(idx);
          } else {
              $scope.selection.splice(pos, 1);
          }
      };*/
  }
  dataLocalUrl: SafeResourceUrl;
  pdf: PDF;
  url: string;
  imageUrl: string;
  fieldsFormat() {
    console.log(this.formFields);
    this.formFields.forEach((field) => {
    if (field.type.name === 'enum') {
        if (field.id == "scientificAreas__") {
          const array = [];
          const enumValues = Object.entries(field.type.values);

          for (const value of enumValues) {
            console.log(value[0]);
            console.log(value[1]);
            array.push({ item_id: value[0], item_text: value[1] });
          }
          this.enumLabels.push(field.label);
          this.enumNames.push(field.id);
          this.dropdownList.push(array);
          this.selectedItems.push([]);
          console.log(this.dropdownList);
        }
      }
      if (field.id == "decision") {
        const array = [];
        const enumValues = Object.entries(field.type.values);

        for (const value of enumValues) {
          console.log(value[0]);
          console.log(value[1]);
          this.dropdownList.push(value[0]);
        }
        this.enumLabels.push(field.label);
        this.enumNames.push(field.id);
        //this.dropdownList.push(array);
        this.selectedItems.push([]);
        console.log(this.dropdownList);
      }
    
      if(field.id =="pdf" || field.id=="pdf3" ){
        this.hasPDF = true;
        
      }
      if(field.id =="pdf1" || field.id=="pdf3" || field.id=="pdf4"){
        //this.url =this.sanitizer.bypassSecurityTrustResourceUrl(field.value.value);
        this.url=field.value.value;
        /*this.paperService.getPDF(field.value.value).subscribe(data=>{
          this.imageUrl = "data:application/pdf;base64," + data.data;
          var file3 = new Blob([data.data], {type: 'application/pdf'});
          //console.log(data.data); 
          //this.dataLocalUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(file3));
          this.dataLocalUrl = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(file3));
          var a = document.createElement("a");
            document.body.appendChild(a);
           
            var fileURL =window.URL.createObjectURL(file3);
                a.href = fileURL;
                a.download = "name";
                a.click();
        })
        
        */
      }
    });
  }

  handleFileInput(file:FileList){
    this.fileToUpload =file.item(0);
    var reader = new FileReader();
    reader.onload=(event:any)=>{
      this.fileUrl =event.target.result;
    }
    reader.readAsDataURL(this.fileToUpload);
    console.log("URL "+this.fileUrl);
    console.log("file "+this.fileToUpload);
    }

    download() {
      
      this.paperService.downloadFile(this.url).subscribe( (data: Blob )=> {
        //let blob:any = new Blob([response.blob()], { type: 'text/json; charset=utf-8' });
        //const url= window.URL.createObjectURL(blob);
        //window.open(url);
  
        
        
        var file = new Blob([data], { type: 'application/pdf' })
        var fileURL = URL.createObjectURL(file);
  
  
        //window.location.href = data.type;
  
        window.open(fileURL); 
        var a         = document.createElement('a');
        a.href        = fileURL; 
        a.target      = '_blank';
        a.download    = 'paper.pdf';
        document.body.appendChild(a);
        a.click();
        
        //fileSaver.saveAs(blob, 'employees.json');
      }, error =>  {
        console.log('Error downloading the file');
      });  
    }
    
}

