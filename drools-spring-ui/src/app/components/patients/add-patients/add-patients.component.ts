import { Patient } from '../../../model/patient';
import { LoginService } from '../../../service/login.service';
import { PatientService } from '../../../service/patient.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-patients',
  templateUrl: './add-patients.component.html',
  styleUrls: ['./add-patients.component.css']
})
export class AddPatientsComponent implements OnInit {

  patients: Patient[];
  patient: Patient;
  role: string;
  display: string;

  constructor(private patientService: PatientService, private router: Router,
              private loginService: LoginService) {
    this.patients = [];
    this.patient = new Patient();
    this.display = 'none';
    this.role = '';
  }

  ngOnInit() {
    this.role = this.loginService.getRole();
    this.getPatients();
  }

  getPatients() {
    this.patientService.getPatients().then(
      res => {
        this.patients = res;
      }
    );
  }

  openModal() {
    this.patient = new Patient();
    this.display = 'block';
  }

  onModalClose() {
    this.display = 'none';
  }

  gotoPatient(id: number) {
    this.router.navigate(['../patients', id]);
  }

  addPatient() {
    this.patientService.addPatient(this.patient).then(
      res => {
      this.getPatients();
      this.display = 'none';
    });
  }

  deletePatient(id: number){
    this.patientService.deletePatient(id).then(
      res => {
        this.getPatients();
      }
    );
  }
}
