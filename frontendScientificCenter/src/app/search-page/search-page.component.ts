import { Component, OnInit } from '@angular/core';
import { CombineQuery } from '../model/CombineQuery';
import { BooleanQuery } from '../model/BooleanQuery';
import { SearchService } from '../service/searchService/search.service';
import { PaperResponse } from '../model/PaperResponse';
import { Paper } from '../model/Paper';
import { PaperService } from '../service/paperService/paper.service';
import { TokenStorageService } from '../auth/token-storage.service';

@Component({
  selector: 'app-search-page',
  templateUrl: './search-page.component.html',
  styleUrls: ['./search-page.component.css']
})
export class SearchPageComponent implements OnInit {
  private searchFields = new CombineQuery();
  private firstQuery = new BooleanQuery();
  private response: Array<PaperResponse>=[];
  private types= [{"name":"title","value":"title"},
                  {"name":"journal title","value":"journaltitle"},
                  {"name":"authors","value":"authors"},
                  {"name":"keywords","value":"keywords"},
                  {"name":"content","value":"content"},
                  {"name":"scientific area","value":"area"},
                  {"name":"everything","value":"everything"}];
  private operators=["AND","OR"];
  private someoneLogged : boolean;

  items = [];
  pageOfItems: Array<any>;
  constructor(private searchService: SearchService, private paperService: PaperService, private tokenStorage : TokenStorageService) { }

  ngOnInit() {
   // this.items = Array(150).fill(0).map((x, i) => ({ id: (i + 1), name: `Item ${i + 1}`}));
   if (this.tokenStorage.getToken()) {
    this.someoneLogged = true;
   }
  }

  onChangePage(pageOfItems: Array<any>) {
    // update current page of items
    this.pageOfItems = pageOfItems;
}

  add(){
    let newM = new BooleanQuery();
   
    this.searchFields.booleanQueryies.push(newM);
  }

  delete(newM: number){
   
    this.searchFields.booleanQueryies.splice(newM,1);
  }

  private valid = true;
  validation(){
    
    if(this.firstQuery.area==undefined || this.firstQuery.query==undefined){
      this.valid=false;
      //console.log(this.firstQuery.area+" "+this.firstQuery.query)
      //console.log(this.valid);
    }
    if(this.searchFields.booleanQueryies.length==1){
      this.searchFields.booleanQueryies.forEach(
        query=>{
          //console.log(query.area+" "+query.query)
          if(query.area==undefined|| query.query==undefined){
            this.valid = false;
            //console.log(this.valid);
          }
        }
      )  
    }else{
      this.searchFields.booleanQueryies.forEach(
        query=>{
          //console.log(query.area+" "+query.query +" "+query.operator)
          if(query.area==undefined|| query.query==undefined || query.operator==undefined){
            this.valid = false;
            //console.log(this.valid);
          }
        }
      )  
    }
    
    console.log(this.valid);
    return this.valid;
  }

  search(){
    this.valid=true;
    if(this.searchFields.booleanQueryies.length>0){
      this.firstQuery.operator=this.searchFields.booleanQueryies[0].operator;//postaviti za prvi takodje onog drugog
      
    }
    this.searchFields.booleanQueryies.unshift(this.firstQuery);    
    
    console.log(this.searchFields.booleanQueryies);
    if(this.validation()){
      this.searchService.search(this.searchFields).subscribe(
        data =>{
          //console.log(data);
          this.response=data;
          this.items = data;
          console.log(this.response);
        }
      )
    }
    //this.searchFields.booleanQueryies = [];
    //this.firstQuery = new BooleanQuery();
    this.searchFields.booleanQueryies.shift();
  }

  buy(paper){

  }

  download(paper: Paper){
    console.log(paper.idPaper);
    this.paperService.getURI(paper.idPaper).subscribe(
      data=>{
        console.log(data.url);
        this.paperService.downloadFile(data.url).subscribe( (data: Blob )=> {
          //let blob:any = new Blob([response.blob()], { type: 'text/json; charset=utf-8' });
          //const url= window.URL.createObjectURL(blob);
          //window.open(url);
    
          
          console.log(data);
          var file = new Blob([data], { type: 'application/pdf' })
          var fileURL  = URL.createObjectURL(file);
    
          console.log(fileURL);
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
    )
      
    /*this.paperService.downloadPDF("D:\\naucnacentrala-upp-novi\\naucnaCentrala\\scientificCenter\\scientificCenter\\files\\astronomija.pdf").subscribe( (data: Blob )=> {
      //let blob:any = new Blob([response.blob()], { type: 'text/json; charset=utf-8' });
      //const url= window.URL.createObjectURL(blob);
      //window.open(url);

      
      console.log(data);
      var file = new Blob([data], { type: 'application/pdf' })
      var fileURL  = URL.createObjectURL(file);

      console.log(fileURL);
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
    });*/  
  }
}
