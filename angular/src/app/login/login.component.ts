import { Component, OnInit } from '@angular/core';
import {LoginService} from "../login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user ={
    email: "janeDoe@kanteen.com",
    password:"zzz",
  }

  constructor(public service:LoginService) { }

  ngOnInit() {
  }

  onSubmit(){
    this.service.checkLoginStatus(this.user.email);
    (this.isAdmin())? this.service.getAdminByEmail(this.user.email): this.service.getParentByEmail(this.user.email);
  }

  isAdmin(){
    return this.service.isAdmin;
  }

  isLogged(){
    return this.service.isLogged;
  }

}
