import { Medicament } from '../../../model/medicament';
import { MedicamentAllergy } from '../../../model/medicament-allergy';
import { Patient } from '../../../model/patient';
import { LoginService } from '../../../service/login.service';
import { MedicamentService } from '../../../service/medicament.service';
import { PatientService } from '../../../service/patient.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-medicaments',
  templateUrl: './medicaments.component.html',
  styleUrls: ['./medicaments.component.css']
})
export class MedicamentsComponent implements OnInit {

  medicaments: Medicament[];
  medicament: Medicament;

  display: string;

  addAllergy: boolean;
  patientId: string;
  medicamentId: number;

  patients: Patient[];

  medicamentAllergy: MedicamentAllergy;

  displayError: string;
  role: string;
  updateMed: boolean;

  constructor(private medicamentService: MedicamentService,
              private router: Router, private loginService: LoginService,
              private patientService: PatientService) {
    this.medicaments = [];
    this.medicament = new Medicament();
    this.display = 'none';
    this.addAllergy = false;
    this.patientId = '';
    this.medicamentId = -1;
    this.patients = [];
    this.medicamentAllergy = new MedicamentAllergy();
    this.displayError = 'none';
    this.role = '';
    this.updateMed = false;
  }

  ngOnInit() {
    this.role = this.loginService.getRole();
    this.getMedicaments();
  }

  getMedicaments() {
    this.medicamentService.allMedicaments().then(
      medicaments => {
        this.medicaments = medicaments;
      }
    );
  }

  deleteMedicament(id: number) {
    this.medicamentService.deleteMedicament(id).then(
      res => {
        this.getMedicaments();
      }
    );
  }

  openModal() {
    this.medicament = new Medicament();
    this.addAllergy = false;
    this.updateMed = false;
    this.display = 'block';
  }

  update(id: number) {
    this.medicamentService.oneMedicament(id).then(
      res => {
        this.medicament = res;
        this.addAllergy = false;
        this.updateMed = true;
        this.display = 'block';
      }
    );

  }

  onModalClose() {
    this.display = 'none';
  }

  gotoIngredients(id: number) {
    this.router.navigate(['../medicaments/ingridients', id]);
  }

  saveMedicament() {
    if (this.updateMed) {
      this.change();
    }else{
      this.medicamentService.addMedicament(this.medicament).then(
      res => {
      this.getMedicaments();
      this.display = 'none';
    });
    }
    
  }

  change() {
    this.medicamentService.changeMedicament(this.medicament).then(
      res => {
      this.getMedicaments();
      this.display = 'none';
    });
  }

  addMedicamentAllergic(id: number, name: string) {
    this.medicamentId = id;
    this.medicamentAllergy.medicamentId = id;
    this.medicamentAllergy.medicamentName = name;
    this.patientService.getPatients().then(
      res => {
        this.patients = res;
        if (res.length > 0) {
          this.medicamentAllergy.patientId = this.patients[0].id;
          this.addAllergy = true;
          this.display = 'block';
        }

      }
    );
  }

  saveAllergy() {
    this.patientService.addMedicamentAllergy(this.medicamentAllergy).then(
      res => {
      this.display = 'none';
    }).catch(
      res => {
        this.displayError = 'block';
      });
  }

  onModalErrorClose() {
    this.displayError = 'none';
  }
}
