import { Illness } from '../../../model/illness';
import { Medicament } from '../../../model/medicament';
import { Patient } from '../../../model/patient';
import { Record } from '../../../model/record';
import { Symptom } from '../../../model/symptom';
import { User } from '../../../model/user';
import { IllnessService } from '../../../service/illness.service';
import { LoginService } from '../../../service/login.service';
import { MedicamentService } from '../../../service/medicament.service';
import { PatientService } from '../../../service/patient.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Location} from '@angular/common';


@Component({
  selector: 'app-diagnose-illness',
  templateUrl: './diagnose-illness.component.html',
  styleUrls: ['./diagnose-illness.component.css']
})
export class DiagnoseIllnessComponent implements OnInit {

  symptoms: Symptom[];

  symptom: Symptom;
  newSymptom: Symptom;

  tempChosen: boolean;
  allIllnesses: Illness[];
  illness: Illness;

  display: string;
  displaySym: string;

  diagnostic: boolean;

  patient: Patient;
  record: Record;

  showSymptoms: boolean;
  illnessSymptoms: Symptom[];

  medicaments: Medicament[];
  chosenMedicaments: Medicament[];
  displayMedicaments: string;
  chosenIllness: Illness;
  chosenMedicament: Medicament;

  errorMsg: string;
  displayAllergy: string;

  patients: Patient[];
  redirectToPatients: boolean;
  doctor: User;

  illnessesDB: Illness[];
  
  constructor( private illnessService: IllnessService, private patientService: PatientService,
               private router: Router, private medicamentService: MedicamentService,
               private loginService: LoginService, private location: Location ) {
    this.tempChosen = false;
    this.symptom = new Symptom();
    this.symptoms = [];
    this.allIllnesses = [];
    this.illness = new Illness();
    this.display = 'none';
    this.displaySym = 'none';
    this.displayMedicaments = 'none';
    this.displayAllergy = 'none';
    this.diagnostic = false;
    this.patient = new Patient();
    this.record = new Record();
    this.showSymptoms = false;
    this.illnessSymptoms = [];
    this.medicaments = [];
    this.chosenMedicaments = [];
    this.chosenIllness = new Illness();
    this.chosenMedicament = new Medicament();
    this.errorMsg = '';
    this.patients = [];
    this.redirectToPatients = false;
    this.doctor = new User();
    this.illnessesDB = [];
  }

  ngOnInit() {
    this.getMedicaments();
    this.getPatients();
    this.getAllIllnessesDB();
    this.doctor.id = this.loginService.getId();
    this.doctor.username = this.loginService.getUsername();
  }

  newSyptom() {
    this.symptoms.push(this.symptom);
    this.symptom = new Symptom();
    this.tempChosen = false;
  }

  termChanged() {
    if (this.symptom.term === 'TEMPERATURE') {
      this.tempChosen = true;
    } else {
      this.tempChosen = false;
    }
  }

  remove(symptom: Symptom) {
    const index: number = this.symptoms.indexOf(symptom);
    if (index !== -1) {
        this.symptoms.splice(index, 1);
    }
  }

  getAll() {
    this.diagnostic = false;
    this.illnessService.allIllnesses(this.symptoms, this.patient.id)
      .then( res => {
        this.allIllnesses = res;
        this.display = 'block';
      });
  }

  getPatients() {
    this.patientService.getPatients().then(
      res => {
        this.patients = res;
        if (res.length > 0) {
          this.patient = this.patients[0];
        }
      }
    ).catch(
      res => {
        this.redirectToPatients = true;
        this.errorMsg = 'Please register patients in order to set a diagnose!';
        this.displayAllergy = 'block';
      }
    );
  }

  getOne() {
    this.diagnostic = false;
    this.illnessService.oneIllness(this.symptoms, this.patient.id)
      .then( res => {
        this.allIllnesses = res;
        this.display = 'block';
      })
      .catch(
       res => {
         this.errorMsg = 'There is no illness found for given symptoms!';
         this.displayAllergy = 'block';
       }
     );
  }

  diagnoseAlone() {
    this.diagnostic = true;
    this.display = 'block';
  }


  onModalClose() {
    this.display = 'none';
  }

  choose(illness: Illness) {
    this.chosenIllness = illness;
    this.chosenMedicaments = [];
    this.displayMedicaments = 'block';
  }

  setDiagnostic() {
    this.illness.symptoms = this.symptoms;

    this.setIllnessCategory();

    this.chosenIllness = this.illness;

    this.chosenMedicaments = [];

    this.displayMedicaments = 'block';
  }

  getAllSymptoms() {
    this.setIllnessCategory();
    this.illnessService.getSymptoms(this.illness)
      .then( res => {
        this.illnessSymptoms = res;
        this.displaySym = 'block';
      });
  }

  onModalSymptomsClose() {
    this.chosenIllness = new Illness();
    this.illnessSymptoms = [];
    this.displaySym = 'none';
  }

  setIllnessCategory() {

    const name = this.illness.name;
    if (name === 'Cold' || name === 'Fever' || name === 'Sinus Infection' || name === 'Tonsil Inflammation') {
      this.illness.category = 'FIRST';
    } else if (name === 'Diabetes' || name === 'Hypertension') {
      this.illness.category = 'SECOND';
    } else if (name === 'Acute Kidney Injury' || name === 'Kidney Disease') {
      this.illness.category = 'THIRD';
    }
  }

  getMedicaments() {
    this.medicamentService.allMedicaments().then(
      medicaments => {
        this.medicaments = medicaments;
        if (medicaments.length > 0) {
          this.chosenMedicament = this.medicaments[0];
        }
      }
    );
  }


  onModalMedicamentsClose() {
    this.displayMedicaments = 'none';
  }

  onModalAllergyClose() {
    if (this.redirectToPatients) {
      this.router.navigate(['../patients']);
    }
    this.displayAllergy = 'none';
  }

  addMed() {
    this.chosenMedicaments.push(this.chosenMedicament);
  }

  finishRecord() {
    this.record.illness = this.chosenIllness;
    this.record.patient = this.patient;
    this.record.medicaments = this.chosenMedicaments;
    this.illnessService.setDiagnostic(this.record).then(
      res => {
        this.display = 'none';
        this.displayMedicaments = 'none';
        this.router.navigate(['../']);
      })
     .catch(
       res => {
         console.log(res.error);
         this.errorMsg = res.error;
         this.displayAllergy = 'block';
         this.chosenMedicaments = [];
       }
     );

  }

  getAllIllnessesDB() {
    this.illnessService.getAllIllnessDB().then(
      res => {
        this.illnessesDB = res;
      }
    );
  }

  finishRecord2() {
    this.record.illness = this.chosenIllness;
    this.record.patient = this.patient;
    this.record.medicaments = this.chosenMedicaments;
    this.record.doctor = this.doctor;
    this.illnessService.diagnose(this.record).then(
      res => {
        this.display = 'none';
        this.displayMedicaments = 'none';
        this.router.navigate(['../diagnoses']);
      })
     .catch(
       res => {
         console.log(res.error);
         this.errorMsg = res.error;
         this.displayAllergy = 'block';
         this.chosenMedicaments = [];
       }
     );

  }

  back() {
    this.location.back();
  }
}
