import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  search: any;
 constructor() { 
  this.search.addWidget(
    instantsearch.widgets.searchBox({
     container: '#search-box'
    })
   );
   // initialize hits widget
   this.search.addWidget(
    instantsearch.widgets.hits({
     container: '#hits',
    })
   ); 
 }
 ngOnInit() {
  const options = environment.algolia;
  this.search = instantsearch(options);
  this.search.start();
 }

 
}
import * as instantsearch from 'instantsearch.js';
declare var instantsearch: any;

export const environment = {
  production: false,
  algolia: {
   appId: 'APP_ID',
   apiKey: 'SEARCH_ONLY_KEY',
   indexName: 'getstarted_actors',
   urlSync: false
  }
 }; 