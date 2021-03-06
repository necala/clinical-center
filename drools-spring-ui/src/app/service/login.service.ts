import { LoginUser } from '../model/login-user';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { User } from '../model/user';
import { HttpParams } from '@angular/common/http';

@Injectable()
export class LoginService {

  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }
  
  public login(username: string, password: string): Promise<User> {

    const params = new HttpParams();
    params.append('username', username);
    params.append('password', password);

   return this.http.post('/api/login', new LoginUser(username, password), {headers: this.headers})
     .toPromise()
     .then(res => res as User);
  }

  public logout(): Promise<{}> {
    localStorage.removeItem('currentUser');

    return this.http.post('/api/logout', {}, {responseType: 'text'})
     .toPromise();
  }

  getUsername(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const username = currentUser && currentUser.username;
    return username ? username : '';
  }

  getRole(): string {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const category = currentUser && currentUser.category;
    return category ? category : '';
  }

  getId(): number {
    const currentUser = JSON.parse(localStorage.getItem('currentUser'));
    const id = currentUser && currentUser.id;
    return id ? id : -1;
  }

  isSignedIn(): boolean {
    return this.getUsername() !== '';
  }

}
