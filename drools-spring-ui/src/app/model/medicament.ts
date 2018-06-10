import { Illness } from './illness';
import { Ingridient } from './ingridient';
import { Patient } from './patient';

export class Medicament {
  id: number;
  name: string;
  category: string;
  ingridients: Ingridient[];

  public constructor() {
    this.id = -1;
    this.name = '';
    this.category = 'OTHER';
    this.ingridients = [];
  }

}
