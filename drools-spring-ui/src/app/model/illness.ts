import { Symptom } from './symptom';
export class Illness {
  id: number;
  name: string;
  category: string;
  symptoms: Symptom[];

  public constructor() {
    this.id = 0;
    this.name = 'Cold';
    this.category = 'FIRST';
    this.symptoms = undefined;
  }
}
