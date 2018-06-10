import { Medicament } from '../../../model/medicament';
import { MedicamentService } from '../../../service/medicament.service';
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


  constructor(private medicamentService: MedicamentService,
              private router: Router) {
    this.medicaments = [];
    this.medicament = new Medicament();
    this.display = 'none';
    this.addAllergy = false;
    this.patientId = '';
    this.medicamentId = -1;
  }

  ngOnInit() {
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
    this.display = 'block';
  }

  onModalClose() {
    this.display = 'none';
  }

  gotoIngredients(id: number) {
    this.router.navigate(['../medicaments/ingridients', id]);
  }

  saveMedicament() {
    this.medicamentService.addMedicament(this.medicament).then(
      res => {
      this.getMedicaments();
      this.display = 'none';
    });
  }

  addMedicamentAllergic(id: number) {
    this.medicamentId = id;
    this.addAllergy = true;
    this.display = 'block';
  }

  saveAllergy() {
    this.medicamentService.addMedicamentAllergy(this.medicamentId, this.patientId).then(
      res => {
      this.display = 'none';
    });
  }
}
