import { Patient } from './patient';
import { Symptom } from './symptom';
export class Illness {
  id: number;
  name: string;
  category: string;
  symptoms: Symptom[];
  patient: Patient;

  public constructor() {
    this.id = 0;
    this.name = 'Cold';
    this.category = 'FIRST';
    this.symptoms = undefined;
    this.patient = new Patient();
  }
}
