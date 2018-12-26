import { Component, OnInit } from '@angular/core';
//import { BsDropdownModule, BsDropdownToggleDirective, BsDropdownMenuDirective } from 'ngx-bootstrap';

@Component({
    selector: 'app-header',
    templateUrl: 'header.component.html'
})

export class HeaderComponent implements OnInit {

    isCollapsed: Boolean = false;
    constructor() { }

    ngOnInit() { }
}
