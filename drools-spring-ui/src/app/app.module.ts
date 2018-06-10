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


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DiagnoseIllnessComponent,
    MedicamentsComponent,
    IngridientsComponent
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
    MedicamentService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
