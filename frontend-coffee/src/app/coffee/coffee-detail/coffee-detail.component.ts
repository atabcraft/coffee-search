import { Component, OnInit } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap';

@Component({
  selector: 'app-coffee-detail',
  templateUrl: './coffee-detail.component.html',
  styleUrls: ['./coffee-detail.component.css']
})
export class CoffeeDetailComponent implements OnInit {

  title: string;
  closeBtnName: string;
  coffee: any;
  coffeeType;
 
  constructor(public bsModalRef: BsModalRef) {}
 
  ngOnInit() {

  }

}
