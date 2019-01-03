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
    principal;

    constructor(private authService: AuthService) { }

    ngOnInit() {
        this.principal = this.authService.getPrincipal(true);
        this.authService.isAuthenticated().subscribe(authDesc => {
            this.isAuthenticated = authDesc.isAuthenticated;
            console.log('Auth flag is:' + authDesc.isAuthenticated);
        });
    }

    doSignOut() {
        this.authService.signOut();
    }
}
