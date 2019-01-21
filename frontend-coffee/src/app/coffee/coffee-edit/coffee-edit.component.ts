import { Component, OnInit } from '@angular/core';
import { ActivatedRouteSnapshot, ActivatedRoute } from '@angular/router';
import { Coffee } from 'src/app/shared/model/coffee.model';

@Component({
  selector: 'app-coffee-edit',
  templateUrl: './coffee-edit.component.html',
  styleUrls: ['./coffee-edit.component.css']
})
export class CoffeeEditComponent implements OnInit {
  coffee: Coffee;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.coffee = this.activatedRoute.snapshot.data.coffee;
    console.log(this.coffee);
  }

  save() {
    console.log('Jes saving');
  }

  goBack() {
    window.history.back();
  }

}
