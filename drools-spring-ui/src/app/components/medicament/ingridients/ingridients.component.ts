import { Ingridient } from '../../../model/ingridient';
import { IngridientAllergy } from '../../../model/ingridient-allergy';
import { Patient } from '../../../model/patient';
import { LoginService } from '../../../service/login.service';
import { MedicamentService } from '../../../service/medicament.service';
import { PatientService } from '../../../service/patient.service';
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
  patients: Patient[];
  ingridientAllergy: IngridientAllergy;
  displayError: string;
  role: string;
  updateIngr: boolean;

  constructor(private medicamentService: MedicamentService, private route: ActivatedRoute,
              private location: Location, private patientService: PatientService,
              private loginService: LoginService) {
    this.displayError = 'none';
    this.ingridients = [];
    this.ingridient = new Ingridient();
    this.display = 'none';
    this.addAllergy = false;
    this.patientId = '';
    this.ingridientId = -1;
    this.patients = [];
    this.ingridientAllergy = new IngridientAllergy();
    this.role = '';
    this.updateIngr = false;
  }

  ngOnInit() {
    if (this.route.snapshot.params['id']) {
      this.id = +this.route.snapshot.params['id'];
    }
    this.getMedicamentIngridients();
    this.role = this.loginService.getRole();
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
    this.updateIngr = false;
    this.display = 'block';
  }

  update(id: number) {
    this.medicamentService.getOneIngridient(this.id, id).then(
      res => {
        this.ingridient = res;
        this.addAllergy = false;
        this.updateIngr = true;
        this.display = 'block';
      }
    );

  }

  onModalClose() {
    this.display = 'none';
  }

  saveIngridient() {
    if (this.updateIngr) {
      this.change();
    } else {
      this.medicamentService.addOneIngridient(this.id, this.ingridient).then(
      res => {
      this.getMedicamentIngridients();
      this.display = 'none';
    });
    }

  }

  change() {
    this.medicamentService.changeOneIngridient(this.id, this.ingridient).then(
      res => {
      this.getMedicamentIngridients();
      this.display = 'none';
    });
  }

  addIngridientAllergic(id: number, name: string) {
    this.ingridientId = id;
    this.ingridientAllergy.ingridientId = id;
    this.ingridientAllergy.ingridientName = name;
    this.patientService.getPatients().then(
      res => {
        this.patients = res;
        if (res.length > 0) {
          this.ingridientAllergy.patientId = this.patients[0].id;
          this.addAllergy = true;
          this.display = 'block';
        }
      }
    );
  }

  saveAllergy() {
    this.patientService.addIngridientAllergy(this.ingridientAllergy).then(
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

  back() {
    this.location.back();
  }
}
