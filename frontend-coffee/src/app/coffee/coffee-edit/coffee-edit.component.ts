import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ICoffee, Coffee } from 'src/app/shared/model/coffee.model';
import { CoffeeConstants } from '../coffee.constants';
import { NgForm } from '@angular/forms';
import { CoffeeService } from '../coffee.service';

@Component({
  selector: 'app-coffee-edit',
  templateUrl: './coffee-edit.component.html',
  styleUrls: ['./coffee-edit.component.css']
})
export class CoffeeEditComponent implements OnInit {
  coffee: ICoffee;
  coffeeTypes;
  currentCoffeeType;

  constructor(private activatedRoute: ActivatedRoute, private coffeeService: CoffeeService) { }

  ngOnInit() {
    this.coffee = this.activatedRoute.snapshot.data.coffee;
    this.coffeeTypes = CoffeeConstants.COFFEE_TYPES.slice().filter(element => element.value != 'ANY');
    this.currentCoffeeType = this.coffeeTypes.find(element => element.value === this.coffee.coffeeType);
    console.log(this.coffee);
  }

  save(f: NgForm) {
    this.coffee.coffeeType = this.currentCoffeeType.value;
    this.coffeeService.update(this.coffee).subscribe(this.onSaveSucces, this.onSaveError);
  }

  goBack() {
    window.history.back();
  }

  onSaveSucces(coffee) {
    this.coffee = coffee;
  }

  onSaveError(error) {
    console.log(error);
  }

}
