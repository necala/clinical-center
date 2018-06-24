import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../model/user';

@Injectable()
export class UserService {

  public headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient, private router: Router) { }
  
  public sendMsg(): Promise<{}> {

    return this.http.post('/send/issue', 'Ovo je neka poruka', {responseType: 'text'})
      .toPromise();
  }

  public register(user: User): Promise<User> {

    return this.http.post('/api/users', user, {headers: this.headers})
      .toPromise()
      .then(res => res as User);
  }

  public getDoctors(): Promise<User[]> {

    return this.http.get('/api/users')
      .toPromise()
      .then(res => res as User[]);
  }

  public getDoctor(id: number): Promise<User> {

    const url = `/api/users/${id}`;

    return this.http.get(url)
      .toPromise()
      .then(res => res as User);
  }

  public updateDoctor(user: User): Promise<User> {

    return this.http.put('/api/users', user)
      .toPromise()
      .then(res => res as User);
  }


  public deleteDoctor(id: number): Promise<{}> {

    const url = `/api/users/${id}`;

    return this.http.delete(url, {responseType: 'text'})
      .toPromise();
  }

}
