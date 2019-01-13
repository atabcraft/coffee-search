import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { createRequestOption } from '../util/request.util';
import { HttpParams } from '@angular/common/http/src/params';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class CoffeeService {
    private API_URL = environment.APP_SERVER;

    constructor(private httpClient: HttpClient) { }

    searchCoffeeWithType(urlParams) {
        const httpParams: HttpParams = createRequestOption(urlParams);
        return this.httpClient.get(
            this.API_URL + '/api/coffees/_search/coffee-with-type',
            {
                params: httpParams
            }
        );
    }
}