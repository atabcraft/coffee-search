import { NgModule } from '@angular/core';

import { CoffeeComponent } from './coffee.component';
import { CoffeeRoutingModule } from './coffee-routing.module';

@NgModule({
    imports: [CoffeeRoutingModule],
    declarations: [CoffeeComponent],
    providers: [],
})
export class CoffeeModule { }
