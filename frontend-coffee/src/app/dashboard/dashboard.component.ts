import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title = 'frontend-coffee';

  userDetails = null;

  constructor( private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.userDetails = this.localStorageService.retrieve('userdetails');
    console.log(this.userDetails);
  }

}
