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

  public allIllnesses(illness: Illness): Promise<Illness[]> {

    return this.http.post('/api/illness/all', illness)
      .toPromise()
      .then(res => res as Illness[]);
  }

  public oneIllness(illness: Illness): Promise<Illness> {

    return this.http.post('/api/illness/one', illness)
      .toPromise()
      .then(res => res as Illness);
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


}
