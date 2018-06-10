import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../model/user';

@Injectable()
export class UserService {

  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) { }

  public register(user: User): Promise<User> {

    return this.http.post('/api/users', user, {headers: this.headers})
      .toPromise()
      .then(res => res as User);
  }
}
