import { Illness } from './illness';
import { Medicament } from './medicament';
import { Patient } from './patient';
import { User } from './user';

export class Record {
  id: number;
  illness: Illness;
  patient: Patient;
  date: Date;
  medicaments: Medicament[];
  doctor: User;

  public constructor() {
    this.id = -1;
    this.illness = new Illness();
    this.patient = new Patient();
    this.date = new Date();
    this.medicaments = [];
    this.doctor = new User();
  }
}
