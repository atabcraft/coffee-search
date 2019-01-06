import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';
import { AuthConstants } from '../auth.constants';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userRoles;
  currentRole;
  errorHappened;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.userRoles = AuthConstants.userRoles;
    this.currentRole = AuthConstants.userRoles[0].value;
  }

  onSignup(form: NgForm) {
    console.log(form);
    this.authService.signUp(form.value).subscribe(resp => {
      this.errorHappened = false;
    },
    err => {
      this.errorHappened = true;
    });
  }

}
