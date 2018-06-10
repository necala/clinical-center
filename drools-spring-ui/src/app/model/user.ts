export class User {
  public id?: number;
  public username?: string;
  public password?: string;
  public firstName?: string;
  public lastName?: string;
  public category?: Category;
  public email?: string;

  constructor() {
    this.id = -1;
    this.username = '';
    this.password = '';
    this.firstName = '';
    this.lastName = '';
    this.category = 1;
    this.email = '';
  }
}

export enum Category {
    ADMIN = 0,
    DOCTOR = 1
}
