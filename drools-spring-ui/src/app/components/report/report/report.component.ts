import { Report } from '../../../model/report';
import { ReportService } from '../../../service/report.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {

   reports: Report[];

  constructor(private reportService: ReportService) {
    this.reports = [];
  }

  ngOnInit() {
    this.getReports();
  }

  getReports() {
    this.reportService.getReports().then(
      res => {
        this.reports = res;
      }
    ).catch(
      res => {
        this.reports = [];
      }
    );
  }

}
