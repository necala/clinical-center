import { Category } from '../../model/user';
import { LoginService } from '../../service/login.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any = {};

  errorModalDisplay: string;

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { }

  ngOnInit() {
    this.errorModalDisplay = 'none';
    this.loginService.logout();
  }

  login() {
    this.loginService.login(this.model.username, this.model.password).then(
      res => {
        if (res.username && res.category) {
          console.log(res.username);
          console.log(res.category);
          localStorage.setItem('currentUser', JSON.stringify({
            id: res.id,
            username: res.username,
            category: res.category
          }));
          if (res.category.toString() === Category[Category.DOCTOR]) {
            this.router.navigate(['../']);
          }
        }
      }
    ).catch( error => {
      console.log(error);
      this.errorModalDisplay = 'block';
    });
  }

  onErrorModalClose(): void {
    this.errorModalDisplay = 'none';
  }

}
