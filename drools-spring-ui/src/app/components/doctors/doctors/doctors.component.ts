import { User } from '../../../model/user';
import { UserService } from '../../../service/user.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-doctors',
  templateUrl: './doctors.component.html',
  styleUrls: ['./doctors.component.css']
})
export class DoctorsComponent implements OnInit {

  doctors: User[];
  doctor: User;

  displayError: string;
  errorMsg: string;
  updateDoctor: boolean;

  constructor(private userService: UserService,
              private router: Router) {
    this.doctors = [];
    this.doctor = new User();
    this.displayError = 'none';
    this.updateDoctor = false;
    this.errorMsg = '';
  }

  ngOnInit() {
    this.getDoctors();
  }

  getDoctors() {
    this.userService.getDoctors().then(
      res => {
        this.doctors = res;
      }
    );
  }

  deleteDoctor(id: number) {
    this.userService.deleteDoctor(id).then(
      res => {
        this.getDoctors();
      }
    ).catch(
      res => {
        this.errorMsg = 'Cannot delete doctor!';
        this.displayError = 'block';
      }
    );
  }

  register() {
    this.router.navigate(['../register']);
  }

  update(id: number) {
    this.router.navigate(['../register', id]);
  }


  onModalErrorClose() {
    this.displayError = 'none';
  }

}
