import { Diagnose, DiagnoseSymptom, DiagnoseMedicament } from '../../../model/diagnose';
import { DiagnoseService } from '../../../service/diagnose.service';
import { LoginService } from '../../../service/login.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-diagnoses',
  templateUrl: './diagnoses.component.html',
  styleUrls: ['./diagnoses.component.css']
})
export class DiagnosesComponent implements OnInit {

  diagnoses: Diagnose[];
  role: string;
  symptoms: DiagnoseSymptom[];
  medicaments: DiagnoseMedicament[];
  errorMsg: string;
  displayError: string;
  displayTable: string;
  showSymtpoms: boolean;

  constructor(private dignoseService: DiagnoseService,
              private router: Router, private loginService: LoginService) {
    this.diagnoses = [];
    this.role = '';
    this.symptoms = [];
    this.medicaments = [];
    this.errorMsg = '';
    this.displayError = 'none';
    this.displayTable = 'none';
    this.showSymtpoms = false;

  }

  ngOnInit() {
    this.role = this.loginService.getRole();
    this.getDiagnoses();
  }

  getDiagnoses() {
    this.dignoseService.allDiagnoses().then(
      res => {
        this.diagnoses = res;
      }
    ).catch(
      res => {
      	this.diagnoses = [];
    });
  }

  deleteDiagnose (id: number) {
    this.dignoseService.deleteDiagnose(id).then(
      res => {
        this.getDiagnoses();
      }
    );
  }

  newDiagnose() {
    this.router.navigate(['../diagnose-illness']);
  }

  getSymptoms (id: number) {
    this.dignoseService.diagnoseSymptoms(id).then(
      res => {
        this.symptoms = res;
        this.showSymtpoms = true;
        this.displayTable = 'block';
      }
    ).catch(
      res => {
        this.symptoms = [];
        this.errorMsg = 'There are no related symptoms!';
        this.displayError = 'block';
      }
    );
  }

  getMedicaments (id: number) {
    this.dignoseService.diagnoseMedicaments(id).then(
      res => {
        this.medicaments = res;
        this.showSymtpoms = false;
        this.displayTable = 'block';
      }
    ).catch(
      res => {
        this.medicaments = [];
        this.errorMsg = 'There are no prescribed medicaments!';
        this.displayError = 'block';
      }
    );
  }

  onModalErrorClose() {
    this.displayError = 'none';
  }

  onModalClose() {
    this.displayTable = 'none';
  }
}
