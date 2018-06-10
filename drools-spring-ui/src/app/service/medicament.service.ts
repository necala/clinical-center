import { Illness } from '../model/illness';
import { Ingridient } from '../model/ingridient';
import { Medicament } from '../model/medicament';
import { Symptom } from '../model/symptom';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { Record } from '../model/record';

@Injectable()
export class MedicamentService {

  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) { }

  public allMedicaments(): Promise<Medicament[]> {

    return this.http.get('/api/medicaments')
      .toPromise()
      .then(res => res as Medicament[]);
  }

  public oneMedicament(id: number): Promise<Medicament> {

    const url = `/api/medicaments/${id}`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Medicament);
  }

  public addMedicament(medicament: Medicament): Promise<Medicament> {

    return this.http.post('/api/medicaments', medicament)
      .toPromise()
      .then(res => res as Medicament);
  }

  public addIngridients(id: number, ingridients: Ingridient[]): Promise<Ingridient[]> {

    const url = `/api/medicaments/${id}/ingridients`;

    return this.http.post(url, ingridients)
      .toPromise()
      .then(res => res as Ingridient[]);
  }

  public addOneIngridient(id: number, ingridient: Ingridient): Promise<Ingridient> {

    const url = `/api/medicaments/${id}/ingridient`;

    return this.http.post(url, ingridient)
      .toPromise()
      .then(res => res as Ingridient);
  }

  public deleteOneIngridient(idM: number, idI: number): Promise<{}> {

    const url = `/api/medicaments/${idM}/ingridient/${idI}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }

  public getIngridients(id: number): Promise<Ingridient[]> {

    const url = `/api/medicaments/${id}/ingridients`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Ingridient[]);
  }

  public deleteMedicament(id: number): Promise<{}> {

    const url = `/api/medicaments/${id}`;

    return this.http
      .delete(url, {responseType: 'text'})
      .toPromise();
  }

  public addMedicamentAllergy(id: number, patientId: string): Promise<{}> {

    const url = `/api/medicaments/${id}/allergies/${patientId}`;

    return this.http.get(url, {responseType: 'text'})
      .toPromise();
  }

  public addIngridientAllergy(id: number, idI: number, patientId: string): Promise<{}> {

    const url = `/api/medicaments/${id}/ingridients/${idI}/allergies/${patientId}`;

    return this.http.get(url, {responseType: 'text'})
      .toPromise();
  }
}
