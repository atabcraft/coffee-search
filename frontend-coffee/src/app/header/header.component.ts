import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
//import { BsDropdownModule, BsDropdownToggleDirective, BsDropdownMenuDirective } from 'ngx-bootstrap';

@Component({
    selector: 'app-header',
    templateUrl: 'header.component.html'
})

export class HeaderComponent implements OnInit {

    isCollapsed: Boolean = false;
    isAuthenticated = false;

    constructor(private authService: AuthService) { }

    ngOnInit() {
        this.isAuthenticated = this.authService.isAuthenticated();
    }

    doSignOut(){
        this.authService.signOut();
    }
}
