import { Diagnose, DiagnoseSymptom, DiagnoseMedicament } from '../model/diagnose';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable()
export class DiagnoseService {

  constructor(private http: HttpClient, private router: Router) { }

  public allDiagnoses(): Promise<Diagnose[]> {

    return this.http.get('/api/diagnoses')
      .toPromise()
      .then(res => res as Diagnose[]);
  }

  public oneDiagnose(id: number): Promise<Diagnose> {

    const url = `/api/diagnoses/${id}`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as Diagnose);
  }

  public deleteDiagnose(id: number): Promise<{}> {

    const url = `/api/diagnoses/${id}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }

  public diagnoseSymptoms(id: number): Promise<DiagnoseSymptom[]> {

    const url = `/api/diagnoses/${id}/symptoms`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as DiagnoseSymptom[]);
  }

  public diagnoseMedicaments(id: number): Promise<DiagnoseMedicament[]> {

    const url = `/api/diagnoses/${id}/medicaments`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as DiagnoseMedicament[]);
  }

}
