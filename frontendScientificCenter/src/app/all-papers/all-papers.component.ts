import { Component, OnInit } from '@angular/core';
import { CombineQuery } from '../model/CombineQuery';
import { BooleanQuery } from '../model/BooleanQuery';
import { SearchService } from '../service/searchService/search.service';
import { PaperResponse } from '../model/PaperResponse';
import { Paper } from '../model/Paper';
import { PaperService } from '../service/paperService/paper.service';
import { TokenStorageService } from '../auth/token-storage.service';
@Component({
  selector: 'app-all-papers',
  templateUrl: './all-papers.component.html',
  styleUrls: ['./all-papers.component.css']
})


export class AllPapersComponent implements OnInit {
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

  items =[];
  pageOfItems: Array<any>;
  constructor(private searchService: SearchService, private paperService: PaperService, private tokenStorage : TokenStorageService) { 
    this.paperService.findAllPapers().subscribe(
      data=>{
        this.items=data;
        console.log(data);
      }
    )
  }

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

  buy(paper){

  }

  filterRecenzents(paper: Paper){
    console.log(paper.idPaper);
    window.location.href="https://localhost:4202/filterPage/"+paper.id;
  }

  reject(paper: Paper){
    this.paperService.indexReject(paper.id).subscribe(
      data=>{
        alert("Succesfully indexed!");
      }

    )
  }
  accept(paper: Paper){
    this.paperService.indexAccept(paper.id).subscribe(
      data=>{
        alert("Succesfully indexed!");
      }

    )
  }
}
