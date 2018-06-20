import { Patient } from './patient';

export class Report {
  id: number;
  category: string;
  patient: Patient;
  helper: string;

  public constructor() {
    this.id = -1;
    this.patient = new Patient();
    this.category = 'HRONIC_ILLNES';
    this.helper = '';
  }
}
