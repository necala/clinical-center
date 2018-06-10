import { User, Category } from '../../model/user';
import { LoginService } from '../../service/login.service';
import { UserService } from '../../service/user.service';
import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  model: User;

  modalDisplay: string;
  repeatedPassword: string;

  constructor(private userService: UserService, private location: Location,
              private loginService: LoginService, private router: Router) {
    this.modalDisplay = 'none';
    this.model = new User();
  }

  ngOnInit() {
  }

  register() {
    if (this.fieldsAreFilled() && this.passwordsMatch()) {
      this.userService.register(this.model).then(
        response => {
          this.model = response;
          this.loginService.login(response.username, response.password).then(
            res => {
              if (res) {
                localStorage.setItem('currentUser', JSON.stringify({
                  id: res.id,
                  username: res.username,
                  category: res.category
                }));
                if (res.category.toString() === Category[Category.DOCTOR]) {
                  this.router.navigate(['../doctor-homepage']);
                }
              }
            }
           );
        }
      ).catch( error => {
        console.log(error);
        this.modalDisplay = 'block';
      });
    }
  }

  private passwordsMatch(): boolean {
    if (this.model.password === this.repeatedPassword) {
      return true;
    }
    return false;
  }
  private fieldsAreFilled(): boolean {
    if (this.model.firstName !== null && this.model.firstName !== ''
    && this.model.lastName !== null && this.model.lastName !== ''
    && this.model.username !== null && this.model.username !== ''
      && this.model.email !== null && this.model.email !== ''
    && this.model.password !== null && this.model.password !== ''
    && this.repeatedPassword !== null && this.repeatedPassword !== '') {
      return true;
    }
    return false;
  }

  goBack(): void {
    this.location.back();
  }

  closeModal() {
    this.modalDisplay = 'none';
  }
}
