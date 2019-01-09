import { NgModule } from '@angular/core';

import { CoffeeComponent } from './coffee.component';
import { CoffeeRoutingModule } from './coffee-routing.module';
import { SharedModule } from '../shared/shared/shared.module';

@NgModule({
    imports: [CoffeeRoutingModule, SharedModule],
    declarations: [CoffeeComponent],
    providers: [],
})
export class CoffeeModule { }
