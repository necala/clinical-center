import { Illness } from '../model/illness';
import { Symptom } from '../model/symptom';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { Record } from '../model/record';

@Injectable()
export class IllnessService {

  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) { }

  public allIllnesses(symptoms: Symptom[], id: number): Promise<Illness[]> {

    const url = `/api/illness/all/${id}`;

    return this.http.post(url, symptoms)
      .toPromise()
      .then(res => res as Illness[]);
  }

  public oneIllness(symptoms: Symptom[], id: number): Promise<Illness[]> {

    const url = `/api/illness/one/${id}`;

    return this.http.post(url, symptoms)
      .toPromise()
      .then(res => res as Illness[]);
  }

  public setDiagnostic(record: Record): Promise<{}> {

    return this.http.post('/api/illness/diagnose', record, {responseType: 'text'})
      .toPromise();
  }

  public getSymptoms(illness: Illness): Promise<Symptom[]> {

    return this.http.post('/api/illness/symptoms', illness)
      .toPromise()
      .then(res => res as Symptom[]);
  }

  public diagnose(record: Record): Promise<{}> {

    return this.http.post('/api/diagnoses', record, {responseType: 'text'})
      .toPromise();
  }


  public getAllIllnessDB(): Promise<Illness[]> {

    return this.http.get('/api/illnesses')
      .toPromise()
      .then(res => res as Illness[]);
  }

  public getOneIllnessDB(id: number): Promise<Illness> {

    const url = `/api/illnesses/${id}`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Illness);
  }

  public addIllness(illness: Illness): Promise<Illness> {

    return this.http.post('/api/illnesses', illness)
      .toPromise()
      .then(res => res as Illness);
  }
  
  public changeIllness(illness: Illness): Promise<Illness> {

    return this.http.put('/api/illnesses', illness)
      .toPromise()
      .then(res => res as Illness);
  }

  public addOneSymptom(id: number, symptom: Symptom): Promise<Symptom> {

    const url = `/api/illnesses/${id}/symptoms`;

    return this.http.post(url, symptom)
      .toPromise()
      .then(res => res as Symptom);
  }

  public changeOneSymptom(id: number, symptom: Symptom): Promise<Symptom> {

    const url = `/api/illnesses/${id}/symptoms`;

    return this.http.put(url, symptom)
      .toPromise()
      .then(res => res as Symptom);
  }

  public deleteOneSymptom(idI: number, idS: number): Promise<{}> {

    const url = `/api/illnesses/${idI}/symptoms/${idS}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }

  public listSymptoms(id: number): Promise<Symptom[]> {

    const url = `/api/illnesses/${id}/symptoms`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Symptom[]);
  }

  public getOneSymptom(id: number, idI: number): Promise<Symptom> {

    const url = `/api/illnesses/${id}/symptoms/${idI}`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Symptom);
  }

  public deleteIllness(id: number): Promise<{}> {

    const url = `/api/illnesses/${id}`;

    return this.http
      .delete(url, {responseType: 'text'})
      .toPromise();
  }


}
