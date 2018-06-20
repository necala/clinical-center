export class Diagnose {
  id: number;
  illnessName: string;
  patientId: number;
  doctorId: number;
  date: Date;
  doctorName: string;
  patientName: string;

  public constructor() {
    this.id = -1;
    this.illnessName = '';
    this.patientId = -1;
    this.doctorId = -1;
    this.date = new Date();
    this.doctorName = '';
    this.patientName = '';
  }
}

export class DiagnoseMedicament {
  id: number;
  medicamentId: number;
  diagnoseId: number;
  medicamentName: string;
  medicamentCategory: string;

  public constructor() {
    this.id = -1;
    this.medicamentId = -1;
    this.diagnoseId = -1;
    this.medicamentName = '';
    this.medicamentCategory = 'OTHER';
  }
}

export class DiagnoseSymptom {
  id: number;
  diagnoseId: number;
  symptomTerm: string;

  public constructor() {
    this.id = -1;
    this.diagnoseId = -1;
    this.symptomTerm = '';
  }
}
