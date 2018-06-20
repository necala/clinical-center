import { User, Category } from '../../model/user';
import { LoginService } from '../../service/login.service';
import { UserService } from '../../service/user.service';
import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  model: User;

  modalDisplay: string;
  repeatedPassword: string;
  id: number;
  update: boolean;

  constructor(private userService: UserService, private location: Location,
              private loginService: LoginService, private router: Router,
              private route: ActivatedRoute) {
    this.modalDisplay = 'none';
    this.id = 0;
    this.model = new User();
    this.update = false;
  }

  ngOnInit() {
    if (this.route.snapshot.params['id']) {
      this.id = +this.route.snapshot.params['id'];
      this.update = true;
      this.getDoctor();
    }
  }

 getDoctor() {
    this.userService.getDoctor(this.id).then(
      res => {
        this.model = res;
      }
    );
  }

  updateDoctor() {
    this.userService.updateDoctor(this.model).then(
      res => {
        this.router.navigate(['../doctors']);
      }
    );
  }

  register() {
    if (this.fieldsAreFilled() && this.passwordsMatch()) {
      this.userService.register(this.model).then(
        response => {
          this.model = response;
          this.router.navigate(['../doctors']);
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

  back(): void {
    this.location.back();
  }

  closeModal() {
    this.modalDisplay = 'none';
  }
}
