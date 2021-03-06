import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  title = 'frontend-coffee';

  userDetails: Observable<any>| null = null;

  constructor( private localStorageService: LocalStorageService) { }

  ngOnInit() {
    this.userDetails = this.localStorageService.observe('userdetails');
    console.log(this.userDetails);
  }

}
