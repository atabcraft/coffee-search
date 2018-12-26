import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  onSign(form: NgForm) {
    console.log("Sign in attempt");
    console.log(form);
    this.authService.signIn({
      'username': form.value.username,
      'password': form.value.password
    }, {
        'rememberMe': form.value.rememberMe ? form.value.rememberMe : false
    }).subscribe(resp => {
      this.router.navigate(['/dashboard']);
    });
  }

}
