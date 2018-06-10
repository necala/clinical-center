import { DiagnoseIllnessComponent } from './components/illness/diagnose-illness/diagnose-illness.component';
import { LoginComponent } from './components/login/login.component';
import { IngridientsComponent } from './components/medicament/ingridients/ingridients.component';
import { MedicamentsComponent } from './components/medicament/medicaments/medicaments.component';
import { RegisterComponent } from './components/register/register.component';
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
    path: 'diagnose-illness',
    component: DiagnoseIllnessComponent
  },
  {
    path: 'medicaments',
    component: MedicamentsComponent
  },
  {
    path: 'medicaments/ingridients/:id',
    component: IngridientsComponent
  }
];
export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
