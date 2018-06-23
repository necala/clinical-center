import { LoginService } from './service/login.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import * as SockJS from 'sockjs-client';

import * as Stomp from '@stomp/stompjs';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app';
  private serverUrl = 'http://localhost:8080/socket';
  private stompClient;

  displayIssue: string;
  issueMsg: string;

  constructor(
              private loginService: LoginService, private router: Router) {
    this.initializeWebSocketConnection();
    this.displayIssue = 'none';
    this.issueMsg = '';
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

  sendMsg() {
    this.stompClient.send('/send/issue' , {}, 'Ovo je neka poruka');

  }

  onModalIssueClose() {
    this.issueMsg = '';
    this.displayIssue = 'none';
  }

  initializeWebSocketConnection() {
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe('/issue', (message) => {
        if(message.body && that.isSignedIn()) {
          that.issueMsg = message.body;
          that.displayIssue = 'block';
        }
      });

    });


  }

}
