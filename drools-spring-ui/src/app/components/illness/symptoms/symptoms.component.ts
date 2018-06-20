import { Symptom } from '../../../model/symptom';
import { IllnessService } from '../../../service/illness.service';
import { LoginService } from '../../../service/login.service';
import { Component, OnInit } from '@angular/core';
import {Location} from '@angular/common';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-symptoms',
  templateUrl: './symptoms.component.html',
  styleUrls: ['./symptoms.component.css']
})
export class SymptomsComponent implements OnInit {

  id: number;
  symptoms: Symptom[];
  symptom: Symptom;
  display: string;
  displayError: string;
  role: string;
  updateSymptom: boolean;
  tempChosen: boolean;
  other: boolean;
  
  constructor(private illnessService: IllnessService, private route: ActivatedRoute,
              private location: Location, private loginService: LoginService) {
    this.displayError = 'none';
    this.symptoms = [];
    this.symptom = new Symptom();
    this.display = 'none';
    this.role = '';
    this.updateSymptom = false;
    this.tempChosen = false;
    this.other = false;
  }

  ngOnInit() {
    if (this.route.snapshot.params['id']) {
      this.id = +this.route.snapshot.params['id'];
    }
    this.getIllnesSymptoms();
    this.role = this.loginService.getRole();
  }

  getIllnesSymptoms() {
    this.illnessService.listSymptoms(this.id).then(
      res => {
        this.symptoms = res;
      }
    );
  }

  deleteSymptom(idS: number) {
    this.illnessService.deleteOneSymptom(this.id, idS).then(
      res => {
        this.getIllnesSymptoms();
      }
    );
  }

  openModal() {
    this.symptom = new Symptom();
    this.updateSymptom = false;
    this.tempChosen = false;
    this.other = false;
    this.display = 'block';
  }

  update(id: number) {
    this.illnessService.getOneSymptom(this.id, id).then(
      res => {
        this.symptom = res;
        this.updateSymptom = true;
        this.tempChosen = false;
        this.display = 'block';
      }
    );

  }

  onModalClose() {
    this.display = 'none';
  }

  otherChanged() {
    if (this.other) {
      this.other = false;
    }else {
      this.other = true;
    }
  }

  saveSymptom() {
    if (this.updateSymptom) {
      this.change();
    } else {
      this.illnessService.addOneSymptom(this.id, this.symptom).then(
      res => {
      this.getIllnesSymptoms();
      this.display = 'none';
    }).catch(
      res => {
        this.displayError = 'block';
      }
    );
    }

  }

  change() {
    this.illnessService.changeOneSymptom(this.id, this.symptom).then(
      res => {
      this.getIllnesSymptoms();
      this.display = 'none';
    }).catch(
      res => {
        this.displayError = 'block';
      }
    );
  }

  termChanged() {
    if (this.symptom.term === 'TEMPERATURE') {
      this.tempChosen = true;
    } else {
      this.tempChosen = false;
    }
  }

  onModalErrorClose() {
    this.displayError = 'none';
  }

  back() {
    this.location.back();
  }

}
