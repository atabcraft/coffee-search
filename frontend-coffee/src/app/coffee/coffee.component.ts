import { Component, OnInit } from '@angular/core';
import { CoffeeService } from './coffee.service';
import { PAGINATION } from '../util/request.util';

@Component({
    selector: 'app-coffee',
    templateUrl: 'coffee.component.html',
    styleUrls: ['coffee.component.css']
})

export class CoffeeComponent implements OnInit {
    constructor(private coffeeService: CoffeeService) {}

    searchTerm = '';

    coffees = [];

    ngOnInit() {
        console.log("Hello from coffee");
        this.searchCoffee();
    }

    searchCoffee() {
        console.log('You searched for : ' + this.searchTerm );
        this.coffeeService.searchCoffeeWithType({
            query: this.searchTerm,
            coffeeType: 'ANY',
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
}