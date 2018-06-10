export class Symptom {
  term: string;
  temperature: number;
  helper: string;
  specific: boolean;

  public constructor() {
    this.term = 'RUNNY_NOSE';
    this.temperature = 37;
    this.helper = '';
    this.specific = false;
  }
}
