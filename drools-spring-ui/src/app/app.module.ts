import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { routing } from './app.route';
import { LoginComponent } from './components/login/login.component';
import { LoginService } from './service/login.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './components/register/register.component';
import { UserService } from './service/user.service';
import { DiagnoseIllnessComponent } from './components/illness/diagnose-illness/diagnose-illness.component';
import { IllnessService } from './service/illness.service';
import { MedicamentsComponent } from './components/medicament/medicaments/medicaments.component';
import { MedicamentService } from './service/medicament.service';
import { IngridientsComponent } from './components/medicament/ingridients/ingridients.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AddPatientsComponent } from './components/patients/add-patients/add-patients.component';
import { PatientService } from './service/patient.service';
import { OnePatientComponent } from './components/patients/one-patient/one-patient.component';
import { DiagnosesComponent } from './components/diagnoses/diagnoses/diagnoses.component';
import { DiagnoseService } from './service/diagnose.service';
import { SymptomsComponent } from './components/illness/symptoms/symptoms.component';
import { IllnessesComponent } from './components/illness/illnesses/illnesses.component';
import { ReportComponent } from './components/report/report/report.component';
import { ReportService } from './service/report.service';
import { DoctorsComponent } from './components/doctors/doctors/doctors.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DiagnoseIllnessComponent,
    MedicamentsComponent,
    IngridientsComponent,
    AddPatientsComponent,
    OnePatientComponent,
    DiagnosesComponent,
    SymptomsComponent,
    IllnessesComponent,
    ReportComponent,
    DoctorsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    routing,
    NgbModule.forRoot()
  ],
  providers: [
    LoginService,
    UserService,
    IllnessService,
    MedicamentService,
    PatientService,
    DiagnoseService,
    ReportService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
