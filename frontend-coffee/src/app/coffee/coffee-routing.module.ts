import { NgModule, Injectable } from '@angular/core';
import { Routes, RouterModule, Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { CoffeeComponent } from './coffee.component';
import { CoffeeEditComponent } from './coffee-edit/coffee-edit.component';
import { Coffee, ICoffee } from '../shared/model/coffee.model';
import { CoffeeService } from './coffee.service';
import { of } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class CoffeeResolve implements Resolve<ICoffee> {
    constructor(private service: CoffeeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return of(new Coffee());
    }
}

const routes: Routes = [
    {
        path: '',
        component: CoffeeComponent
    },
    {
        path: ':id',
        component: CoffeeEditComponent,
        resolve: {
            coffee: CoffeeResolve
        }
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [],
    declarations: [],
})
export class CoffeeRoutingModule { }