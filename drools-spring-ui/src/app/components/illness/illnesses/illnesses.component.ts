import { Illness } from '../../../model/illness';
import { IllnessService } from '../../../service/illness.service';
import { LoginService } from '../../../service/login.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-illnesses',
  templateUrl: './illnesses.component.html',
  styleUrls: ['./illnesses.component.css']
})
export class IllnessesComponent implements OnInit {

  illnesses: Illness[];
  illness: Illness;

  display: string;

  role: string;
  updateIllness: boolean;

  constructor(private illnessService: IllnessService,
              private router: Router, private loginService: LoginService) {
    this.illnesses = [];
    this.illness = new Illness();
    this.display = 'none';
    this.role = '';
    this.updateIllness = false;
  }

  ngOnInit() {
    this.role = this.loginService.getRole();
    this.getIllnesses();
  }

  getIllnesses() {
    this.illnessService.getAllIllnessDB().then(
      res => {
        this.illnesses = res;
      }
    );
  }

  deleteIllness(id: number) {
    this.illnessService.deleteIllness(id).then(
      res => {
        this.getIllnesses();
      }
    );
  }

  openModal() {
    this.illness = new Illness();
    this.illness.name = '';
    this.updateIllness = false;
    this.display = 'block';
  }

  update(id: number) {
    this.illnessService.getOneIllnessDB(id).then(
      res => {
        this.illness = res;
        this.updateIllness = true;
        this.display = 'block';
      }
    );

  }

  onModalClose() {
    this.display = 'none';
  }

  gotoSymptoms(id: number) {
    this.router.navigate(['../illnesses/symptoms', id]);
  }

  saveIllness() {
    if (this.updateIllness) {
      this.change();
    } else {
      this.illnessService.addIllness(this.illness).then(
      res => {
      this.getIllnesses();
      this.display = 'none';
    });
    }

  }

  change() {
    this.illnessService.changeIllness(this.illness).then(
      res => {
      this.getIllnesses();
      this.display = 'none';
    });
  }

}
