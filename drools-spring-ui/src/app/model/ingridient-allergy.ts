export class IngridientAllergy {
  id: number;
  ingridientId: number;
  ingridientName: string;
  patientId: number;

  public constructor() {
    this.id = -1;
    this.ingridientId = -1;
    this.ingridientName = '';
    this.patientId = -1;
  }
}
