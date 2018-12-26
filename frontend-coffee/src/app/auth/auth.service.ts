import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { createRequestOption } from '../util/request.util';
import { tap, flatMap } from 'rxjs/operators';
import { LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {

    apiUri = environment.APP_SERVER;

    principal = null;

    constructor(private httpClient: HttpClient, private localStorageService: LocalStorageService, private router: Router) { }

    signIn(bodyParms, urlParams): Observable<HttpResponse<any>> {
        const params: HttpParams = createRequestOption( urlParams );
        return this.httpClient.post(
            this.apiUri + '/api/users/sign-in',
            bodyParms,
            {
                observe: 'response',
                'params': params
            }).pipe(
                tap( resp => {
                    this.localStorageService.store('authenticationToken', resp.body.token);
                    this.httpClient.get( this.apiUri + '/api/users/current').subscribe(
                        (userResp: any) =>  {
                            this.localStorageService.store('userDetails', userResp);
                            this.principal = userResp;
                        }
                    );
                })
            )
    }

    signOut() {
        console.log('Starting sign out process');
        this.localStorageService.clear('userDetails');
        this.localStorageService.clear('authenticationtoken');
        this.principal = null;
        this.router.navigate(['/dashboard']);
    }

    isAuthenticated() {
        return this.principal != null
            && this.localStorageService.retrieve('authenticationtoken') != null;
    }
}