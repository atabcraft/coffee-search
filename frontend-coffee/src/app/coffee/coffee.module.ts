import { NgModule } from '@angular/core';

import { CoffeeComponent } from './coffee.component';
import { CoffeeRoutingModule } from './coffee-routing.module';
import { SharedModule } from '../shared/shared.module';
import { CoffeeDetailComponent } from './coffee-detail/coffee-detail.component';
import { CoffeeEditComponent } from './coffee-edit/coffee-edit.component';

@NgModule({
    imports: [CoffeeRoutingModule, SharedModule],
    declarations: [CoffeeComponent,  CoffeeDetailComponent, CoffeeEditComponent],
    entryComponents: [CoffeeDetailComponent],
    providers: [],
})
export class CoffeeModule { }
