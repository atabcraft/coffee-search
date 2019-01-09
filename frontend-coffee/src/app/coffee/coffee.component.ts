import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-coffee',
    templateUrl: 'coffee.component.html',
    styleUrls: ['coffee.component.css']
})

export class CoffeeComponent implements OnInit {
    constructor() { }

    searchTerm = '';

    ngOnInit() {
        console.log("Hello from coffee");
    }

    searchCoffee() {
        console.log('You searched for : ' + this.searchTerm );
    }
}