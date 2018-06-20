import { LoginService } from './service/login.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';

  constructor(
              private loginService: LoginService, private router: Router) {
  }


  isSignedIn(): boolean {
    return this.loginService.isSignedIn();
  }

  isAdminSignedIn(): boolean {
    if (this.loginService.isSignedIn() && this.loginService.getRole() === 'ADMIN') {
      return true;
    }
    return false;
  }
  
  signOut(): void {
    localStorage.removeItem('currentUser');
    this.router.navigateByUrl('/login');
  }
}
