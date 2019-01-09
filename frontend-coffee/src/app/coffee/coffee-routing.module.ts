import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CoffeeComponent } from './coffee.component';

const routes: Routes = [
    {
        path: '',
        component: CoffeeComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [],
    declarations: [],
})
export class CoffeeRoutingModule { }