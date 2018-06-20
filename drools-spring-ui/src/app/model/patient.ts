export class Patient {
  id: number;
  patientId: string;
  firstName: string;
  lastName: string;

  public constructor() {
    this.id = -1;
    this.patientId = '';
    this.firstName = '';
    this.lastName = '';
  }
}
