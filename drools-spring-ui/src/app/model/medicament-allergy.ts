export class MedicamentAllergy {
  id: number;
  medicamentId: number;
  medicamentName: string;
  patientId: number;

  public constructor() {
    this.id = -1;
    this.medicamentId = -1;
    this.medicamentName = '';
    this.patientId = -1;
  }
}
