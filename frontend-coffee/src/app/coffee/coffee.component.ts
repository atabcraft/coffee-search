import { Component, OnInit } from '@angular/core';
import { CoffeeService } from './coffee.service';
import { PAGINATION } from '../util/request.util';
import { CoffeeConstants } from './coffee.constants';
import { BsModalService, BsModalRef } from 'ngx-bootstrap';
import { CoffeeDetailComponent } from './coffee-detail/coffee-detail.component';

@Component({
    selector: 'app-coffee',
    templateUrl: 'coffee.component.html',
    styleUrls: ['coffee.component.css']
})

export class CoffeeComponent implements OnInit {

    constructor(private coffeeService: CoffeeService, private modalService: BsModalService) {}

    searchTerm = '';
    bsModalRef: BsModalRef;
    coffees = [];
    coffeeTypes;
    currentCoffeeType;

    ngOnInit() {
        console.log('Hello from coffee component');
        this.coffeeTypes = CoffeeConstants.COFFEE_TYPES;
        this.currentCoffeeType = this.coffeeTypes[3];
        this.searchCoffee();
    }

    openCoffeeDetail(coffee) {
        const initialState = {
          'coffee': coffee,
          'title': 'Coffee detail',
          'coffeeType': this.determineTypeDescription(coffee)
        };
        this.bsModalRef = this.modalService.show(CoffeeDetailComponent, {initialState});
        this.bsModalRef.content.closeBtnName = 'Close';
      }

    searchCoffee() {
        console.log('You searched for : ' + this.searchTerm  );
        console.log(this.currentCoffeeType);
        // will add real pagination on frontend later
        this.coffeeService.searchCoffeeWithType({
            query: this.searchTerm,
            coffeeType: this.currentCoffeeType.value,
            page : 0,
            size : PAGINATION.PAGE_SIZE,
            sort : PAGINATION.DEFAULT_COFFEE_SORT
        }).subscribe(
            (resp: any) => {
                console.log(resp);
                this.coffees = resp;
            }
        );
    }

    determineTypeDescription(coffee){
        return this.coffeeTypes.find(element => element.value === coffee.coffeeType).desc;
    }
}