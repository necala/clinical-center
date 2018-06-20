import { IngridientAllergy } from '../model/ingridient-allergy';
import { MedicamentAllergy } from '../model/medicament-allergy';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Patient } from '../model/patient';

@Injectable()
export class PatientService {


  constructor(private http: HttpClient, private router: Router) { }

  public addPatient(patient: Patient): Promise<Patient> {

    return this.http.post('/api/patients', patient)
      .toPromise()
      .then(res => res as Patient);
  }

  public getPatients(): Promise<Patient[]> {

    return this.http.get('/api/patients')
      .toPromise()
      .then(res => res as Patient[]);
  }

  public getPatient(id: number): Promise<Patient> {

    const url = `/api/patients/${id}`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Patient);
  }

  public editPatient(patient: Patient): Promise<Patient> {

    return this.http.put('/api/patients', patient)
      .toPromise()
      .then(res => res as Patient);
  }

  public getIngridientAllergies(id: number): Promise<IngridientAllergy[]> {

    const url = `/api/patients/${id}/allergies/ingridients`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as IngridientAllergy[]);
  }

  public getMedicamentAllergies(id: number): Promise<MedicamentAllergy[]> {

    const url = `/api/patients/${id}/allergies/medicaments`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as MedicamentAllergy[]);
  }

  public deleteIngridientAllergy(id: number): Promise<{}> {

    const url = `/api/allergies/ingridients/${id}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }

  public deleteMedicamentAllergy(id: number): Promise<{}> {

    const url = `/api/allergies/medicaments/${id}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }

  public addIngridientAllergy(ia: IngridientAllergy): Promise<IngridientAllergy> {

    return this.http.post('/api/allergies/ingridients', ia)
      .toPromise()
      .then(res => res as IngridientAllergy);
  }

  public addMedicamentAllergy(ma: MedicamentAllergy): Promise<MedicamentAllergy> {


    return this.http.post('/api/allergies/medicaments', ma)
      .toPromise()
      .then(res => res as MedicamentAllergy);
  }

  public deletePatient(id: number): Promise<{}> {

    const url = `/api/patients/${id}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }
}
