import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SignupComponent } from './auth/signup/signup.component';
import { SigninComponent } from './auth/signin/signin.component';
import { DashboardComponent } from './dashboard/dashboard.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'signup', component: SignupComponent },
  { path: 'signin', component: SigninComponent, outlet: 'popup', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'coffee', loadChildren: './coffee/coffee.module#CoffeeModule'}

];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes,  { enableTracing: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
