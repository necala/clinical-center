import { IngridientAllergy } from '../../../model/ingridient-allergy';
import { MedicamentAllergy } from '../../../model/medicament-allergy';
import { Patient } from '../../../model/patient';
import { PatientService } from '../../../service/patient.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-one-patient',
  templateUrl: './one-patient.component.html',
  styleUrls: ['./one-patient.component.css']
})
export class OnePatientComponent implements OnInit {

  id: number;
  patient: Patient;
  ingridientAllergy: IngridientAllergy;
  medicamentAllergy: MedicamentAllergy;
  ingridientAllergies: IngridientAllergy[];
  medicamentAllergies: MedicamentAllergy[];

  displayMedicaments: string;
  displayIngridients: string;

  displayError: string;
  errorMsg: string;

  constructor(private patientService: PatientService, private route: ActivatedRoute,
              private location: Location) {
    this.patient = new Patient();
    this.ingridientAllergy = new IngridientAllergy();
    this.medicamentAllergy = new MedicamentAllergy();
    this.ingridientAllergies = [];
    this.medicamentAllergies = [];
    this.displayIngridients = 'none';
    this.displayMedicaments = 'none';
    this.displayError = 'none';
    this.errorMsg = '';
   }

  ngOnInit() {
    if (this.route.snapshot.params['id']) {
      this.id = +this.route.snapshot.params['id'];
    }
    this.getPatient();
  }

  getPatient() {
    this.patientService.getPatient(this.id).then(
      res => {
        this.patient = res;
      }
    );
  }

  update() {
    this.patientService.editPatient(this.patient).then(
      res => {
        this.patient = res;
        this.errorMsg = 'Update successful!';
        this.displayError = 'block';
      }
    );
  }

  getIngridientAllergies() {
    this.patientService.getIngridientAllergies(this.id).then(
      res => {
        this.ingridientAllergies = res;
      }
    ).catch(
      res => {
        this.errorMsg = 'Patient is not allergic to any ingredient!';
        this.displayIngridients = 'none';
        this.displayError = 'block';
    });
  }

  getMedicamentAllergies() {
    this.patientService.getMedicamentAllergies(this.id).then(
      res => {
        this.medicamentAllergies = res;
      }
    ).catch(
      res => {
        this.errorMsg = 'Patient is not allergic to any medicament!';
        this.displayMedicaments = 'none';
        this.displayError = 'block';
    });
  }

  deleteIngridientAllergy(id: number) {
    this.patientService.deleteIngridientAllergy(id).then(
      res => {
        console.log(res);
        this.getIngridientAllergies();
    });
  }

  deleteMedicamentAllergy(id: number) {
    this.patientService.deleteMedicamentAllergy(id).then(
      res => {
        console.log(res);
        this.getMedicamentAllergies();
    });
  }

  showIngridients() {
    this.patientService.getIngridientAllergies(this.id).then(
      res => {
        this.ingridientAllergies = res;
        this.displayIngridients = 'block';
      }
    ).catch(
      res => {
        this.errorMsg = 'Patient is not allergic to any ingredient!';
        this.displayError = 'block';
    });
  }

  showMedicaments() {
    this.patientService.getMedicamentAllergies(this.id).then(
      res => {
        this.medicamentAllergies = res;
        this.displayMedicaments = 'block';
      }
    ).catch(
      res => {
        this.errorMsg = 'Patient is not allergic to any medicament!';
        this.displayError = 'block';
    });
  }

  onModalIngridientsClose() {
    this.displayIngridients = 'none';
  }

  onModalMedicamentsClose() {
    this.displayMedicaments = 'none';
  }

  onModalErrorClose() {
    this.displayError = 'none';
  }

  back() {
    this.location.back();
  }

}
