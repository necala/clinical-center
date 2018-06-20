import { Injectable } from '@angular/core';
import { Report } from '../model/report';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Injectable()
export class ReportService {

  constructor(private http: HttpClient, private router: Router) { }

  public getReports(): Promise<Report[]> {

    return this.http.get('/api/reports')
      .toPromise()
      .then(res => res as Report[]);
  }

}
