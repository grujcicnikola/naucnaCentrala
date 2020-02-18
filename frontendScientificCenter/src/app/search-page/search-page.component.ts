import { Component, OnInit } from '@angular/core';
import { CombineQuery } from '../model/CombineQuery';
import { BooleanQuery } from '../model/BooleanQuery';
import { SearchService } from '../service/searchService/search.service';
import { PaperResponse } from '../model/PaperResponse';

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
                  {"name":"author","value":"author"},
                  {"name":"keywords","value":"keywords"},
                  {"name":"content","value":"content"},
                  {"name":"area","value":"scientific area"},
                  {"name":"everything","value":"everything"}];
  private operators=["AND","OR"];
  constructor(private searchService: SearchService) { }

  ngOnInit() {
  }

  add(){
    let newM = new BooleanQuery();
   
    this.searchFields.booleanQueryies.push(newM);
  }

  delete(newM: number){
   
    this.searchFields.booleanQueryies.splice(newM,1);
  }

  search(){
    if(this.searchFields.booleanQueryies.length>0){
      this.firstQuery.operator=this.searchFields.booleanQueryies[0].operator;//postaviti za prvi takodje onog drugog
    }
    this.searchFields.booleanQueryies.unshift(this.firstQuery);
    console.log(this.searchFields.booleanQueryies);
    this.searchService.search(this.searchFields).subscribe(
      data =>{
        //console.log(data);
        this.response=data;
        console.log(this.response);
      }
    )
    //this.searchFields.booleanQueryies = [];
    //this.firstQuery = new BooleanQuery();
    this.searchFields.booleanQueryies.shift();
  }
}
