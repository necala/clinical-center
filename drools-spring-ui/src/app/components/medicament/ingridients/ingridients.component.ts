import { Ingridient } from '../../../model/ingridient';
import { MedicamentService } from '../../../service/medicament.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-ingridients',
  templateUrl: './ingridients.component.html',
  styleUrls: ['./ingridients.component.css']
})
export class IngridientsComponent implements OnInit {

  id: number;
  ingridients: Ingridient[];
  ingridient: Ingridient;
  display: string;
  addAllergy: boolean;
  patientId: string;
  ingridientId: number;

  constructor(private medicamentService: MedicamentService, private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit() {
    if (this.route.snapshot.params['id']) {
      this.id = +this.route.snapshot.params['id'];
    }
    this.ingridients = [];
    this.ingridient = new Ingridient();
    this.getMedicamentIngridients();
    this.display = 'none';
    this.addAllergy = false;
    this.patientId = '';
    this.ingridientId = -1;
  }

  getMedicamentIngridients() {
    this.medicamentService.getIngridients(this.id).then(
      res => {
        this.ingridients = res;
      }
    );
  }

  deleteIngridient(idI: number) {
    this.medicamentService.deleteOneIngridient(this.id, idI).then(
      res => {
        this.getMedicamentIngridients();
      }
    );
  }

  openModal() {
    this.ingridient = new Ingridient();
    this.addAllergy = false;
    this.display = 'block';
  }

  onModalClose() {
    this.display = 'none';
  }

  saveIngridient() {
    this.medicamentService.addOneIngridient(this.id, this.ingridient).then(
      res => {
      this.getMedicamentIngridients();
      this.display = 'none';
    });
  }

  addIngridientAllergic(id: number) {
    this.ingridientId = id;
    this.addAllergy = true;
    this.display = 'block';
  }

  saveAllergy() {
    this.medicamentService.addIngridientAllergy(this.id, this.ingridientId, this.patientId).then(
      res => {
      this.display = 'none';
    });
  }

  back() {
    this.location.back();
  }
}
