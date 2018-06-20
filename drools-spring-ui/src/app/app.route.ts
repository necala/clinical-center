import { DiagnosesComponent } from './components/diagnoses/diagnoses/diagnoses.component';
import { DoctorsComponent } from './components/doctors/doctors/doctors.component';
import { AddPatientsComponent } from './components/patients/add-patients/add-patients.component';
import { DiagnoseIllnessComponent } from './components/illness/diagnose-illness/diagnose-illness.component';
import { IllnessesComponent } from './components/illness/illnesses/illnesses.component';
import { SymptomsComponent } from './components/illness/symptoms/symptoms.component';
import { LoginComponent } from './components/login/login.component';
import { IngridientsComponent } from './components/medicament/ingridients/ingridients.component';
import { MedicamentsComponent } from './components/medicament/medicaments/medicaments.component';
import { OnePatientComponent } from './components/patients/one-patient/one-patient.component';
import { RegisterComponent } from './components/register/register.component';
import { ReportComponent } from './components/report/report/report.component';
import { ModuleWithProviders } from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'register/:id',
    component: RegisterComponent
  },
  {
    path: 'diagnose-illness',
    component: DiagnoseIllnessComponent
  },
  {
    path: 'diagnoses',
    component: DiagnosesComponent
  },
  {
    path: 'medicaments',
    component: MedicamentsComponent
  },
  {
    path: 'medicaments/ingridients/:id',
    component: IngridientsComponent
  },
  {
    path: 'illnesses',
    component: IllnessesComponent
  },
  {
    path: 'illnesses/symptoms/:id',
    component: SymptomsComponent
  },
  {
    path: 'patients',
    component: AddPatientsComponent
  },
  {
    path: 'patients/:id',
    component: OnePatientComponent
  },
  {
    path: 'reports',
    component: ReportComponent
  },
  {
    path: 'doctors',
    component: DoctorsComponent
  }
  
];
export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
