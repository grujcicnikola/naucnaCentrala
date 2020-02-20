import { Component, OnInit } from '@angular/core';
import { PaperService } from '../service/paperService/paper.service';
import { ActivatedRoute } from '@angular/router';
import { Paper } from '../model/Paper';
import { IDropdownSettings } from 'ng-multiselect-dropdown';
import { Recenzent } from '../model/Recenzent';
import { SearchService } from '../service/searchService/search.service';

@Component({
  selector: 'app-filter-page',
  templateUrl: './filter-page.component.html',
  styleUrls: ['./filter-page.component.css']
})
export class FilterPageComponent implements OnInit {

  idPaper: number;
  paper: Paper;
  recenzents: Recenzent[];
  selectedItems = [];
  selectedItemsHelp = [];
  dropdownList = [];
  dropdownListHelp = [];
  enumLabels = [];
  enumLabelsHelp = [];
  enumNames = [];
  enumNamesHelp = [];
  enumSingleSelectValues = {};
  enumSingleSelectValuesHelp = {};
  dropdownSettings: IDropdownSettings = {};
  disabled = false;
  ShowFilter = false;
  limitSelection = false;
  recenzentsList:  any[];
  selectedItems1: any;
  dropdownSettings1: any = {};
  constructor(private paperService: PaperService, private router: ActivatedRoute, private searchService: SearchService) {
    
   }

  ngOnInit() {
    this.idPaper = this.router.snapshot.params.id;
    this.paperService.findRecenzentsByPaperId(this.idPaper).subscribe(
      data=>{
        this.recenzents= data;
        console.log(this.recenzents);
        this.fieldsFormat();
      }
    );
    this.paperService.findById(this.idPaper).subscribe(
      data=>{
        this.paper= data;
        console.log(this.paper);
      }
    );
    
  this.dropdownSettings1 = {
      singleSelection: false,
      idField: 'item_id',
      textField: 'item_text',
      selectAllText: 'Select All',
      unSelectAllText: 'UnSelect All',
      itemsShowLimit: 3,
      allowSearchFilter: this.ShowFilter
  };
  }

  fieldsFormat() {
    this.recenzentsList =[];
    this.recenzents.forEach((field) => {
      this.recenzentsList.push({item_id: field.id, item_text: field.name+ " "+field.surname+ ", "+field.city+ ", "+field.country}); 
    });
    
  }

  onSubmit(value, form){
    console.log(this.selectedItems);
  }

  onItemSelect(item: any) {
    console.log('onItemSelect', item);
  }

  filterByArea(){
    this.paperService.choosingReviewersFilteredByScientificAreaPaper(this.idPaper).subscribe(
      data=>{
        this.recenzents= data;
        console.log(this.recenzents);
        this.fieldsFormat();
      }
    )  
    /*this.recenzentsList =[];
    this.recenzentsList.push({item_id: 5, item_text: "bla"}); 
    */
    this.selectedItems=[]
  }

  filterMoreLikeThis(){
    this.searchService.moreLikeThis(this.idPaper).subscribe(
      data=>{
        this.recenzents= data;
        console.log(this.recenzents);
        this.fieldsFormat();
      }
    )  
    /*this.recenzentsList =[];
    this.recenzentsList.push({item_id: 5, item_text: "bla"}); 
    */
    this.selectedItems=[]
  }

  filterGeo(){
    this.searchService.geoPoint(this.idPaper).subscribe(
      data=>{
        this.recenzents= data;
        console.log(this.recenzents);
        this.fieldsFormat();
      }
    )  
    /*this.recenzentsList =[];
    this.recenzentsList.push({item_id: 5, item_text: "bla"}); 
    */
    this.selectedItems=[]
  
  }

  refresh(){
    this.paperService.findRecenzentsByPaperId(this.idPaper).subscribe(
      data=>{
        this.recenzents= data;
        console.log(this.recenzents);
        this.fieldsFormat();
      }
    );
  }
}
