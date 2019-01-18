import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Subscription, Observable, from } from 'rxjs';
import { IUserDetail } from '../shared/model/user-detail.model';
import { mergeMap, tap, switchMap } from 'rxjs/operators';
//import { BsDropdownModule, BsDropdownToggleDirective, BsDropdownMenuDirective } from 'ngx-bootstrap';

@Component({
    selector: 'app-header',
    templateUrl: 'header.component.html',
    styleUrls: ['./header.component.css']
})

export class HeaderComponent implements OnInit, OnDestroy {

    isCollapsed: Boolean = false;
    isAuthenticated = false;
    signOutSubscription: Subscription;
    userDetails: IUserDetail;

    constructor(private authService: AuthService) { }

    ngOnInit() {
        this.authService.getPrincipal(true);
        this.signOutSubscription = this.authService.isAuthenticated().pipe(
            tap( authDesc => this.isAuthenticated = authDesc.isAuthenticated),
            //this actually unsubscribes itself from old observable
            switchMap( isAuthFlag => {
                if ( !isAuthFlag ) {
                    return this.authService.getPrincipal(true);
                }
                return this.authService.getPrincipal(false);
                }
            )
        ).subscribe(userDetails => {
            this.userDetails = userDetails;
        },
        error => console.log(error) );
    }

    doSignOut() {
        this.authService.signOut();
    }

    ngOnDestroy() {
        this.signOutSubscription.unsubscribe();
    }
}
