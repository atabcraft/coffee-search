import { Component, OnInit, OnDestroy, TemplateRef, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { BsModalRef, BsModalService } from 'ngx-bootstrap';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.css']
})
export class SigninComponent implements OnInit {

  @ViewChild('template') public template: TemplateRef<any>;
  modalRef: BsModalRef;

  constructor(private authService: AuthService, private router: Router, private modalService: BsModalService) { }

  ngOnInit() {
    // workaround for issue https://github.com/angular/angular/issues/15634
    setTimeout(() => {
      this.modalRef = this.modalService.show(this.template);
    })
  }

  onSign(form: NgForm) {
    console.log(form);
    this.authService.signIn({
      'username': form.value.username,
      'password': form.value.password
    }, {
        'rememberMe': form.value.rememberMe ? form.value.rememberMe : false
    }).subscribe(resp => {
      this.cleanup();
    });
  }

  onCloseClick(){
    this.cleanup();
  }

  cleanup() {
    this.modalRef.hide();
    this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true });
    this.modalRef = null;
  }

}
