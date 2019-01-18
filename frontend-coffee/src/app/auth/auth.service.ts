import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable, of, Subject } from 'rxjs';
import { createRequestOption } from '../util/request.util';
import { tap, map, multicast, share } from 'rxjs/operators';
import { LocalStorageService } from 'ngx-webstorage';
import { Router } from '@angular/router';
import { IUserDetail } from '../shared/model/user-detail.model';

@Injectable({ providedIn: 'root' })
export class AuthService {

    apiUri = environment.APP_SERVER;
    principal = null;

    isAuthenticatedSubject = new Subject();

    constructor(private httpClient: HttpClient, private localStorageService: LocalStorageService, private router: Router) {
    }

    signIn(bodyParms, urlParams): Observable<HttpResponse<any>> {
        const params: HttpParams = createRequestOption(urlParams);
        return this.httpClient.post(
            this.apiUri + '/api/users/sign-in',
            bodyParms,
            {
                observe: 'response',
                'params': params
            }).pipe(
                tap(resp => {
                    this.localStorageService.store('authenticationToken', resp.body.token);
                    this.getPrincipal(true);
                    this.isAuthenticatedSubject.next({
                        isAuthenticated: true
                    });
                })
            );
    }

    signUp( bodyParams ) {
        return this.httpClient.post(
            this.apiUri + '/api/users/sign-up',
            bodyParams
        ).pipe(
            map( resp => {
                console.log( resp );
                this.router.navigate(['/signin']);
                return resp;
            })
        );
    }

    getPrincipal(forceReload: boolean): Promise<IUserDetail> {
        console.log('calling getPrincipal with flag to forceReload:' + forceReload);
        if (forceReload) {
            return this.refreshCurrentPrincipal();
        }
        return Promise.resolve(this.principal);
    }

    refreshCurrentPrincipal(): Promise<IUserDetail> {
        return this.httpClient.get(this.apiUri + '/api/users/current').toPromise().then(
            (userResp: IUserDetail) => {
                this.localStorageService.store('userDetails', userResp);
                this.principal = userResp;
                this.isAuthenticatedSubject.next({
                    isAuthenticated: true
                });
                return userResp;
            },
            (error => {
                this.principal = null;
                console.log('Error happened while catching current principal, possibly because of 403');
                return error;
            })
        );
    }

    signOut() {
        console.log('Starting sign out process');
        this.localStorageService.clear('userDetails');
        this.localStorageService.clear('authenticationtoken');
        this.principal = null;
        this.router.navigate(['/dashboard']);
        this.isAuthenticatedSubject.next({
            isAuthenticated: false
        });
    }

    isAuthenticated(): Observable<any> {
        return this.isAuthenticatedSubject.asObservable().pipe(share());
    }
}