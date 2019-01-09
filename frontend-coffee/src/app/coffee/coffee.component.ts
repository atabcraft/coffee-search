import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-coffee',
    templateUrl: 'coffee.component.html'
})

export class CoffeeComponent implements OnInit {
    constructor() { }

    ngOnInit() {
        console.log("Hello from coffee");
    }
}