import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginForm!:FormGroup;
  constructor(private fb:FormBuilder) {    
     }
  ngOnInit(): void {
    this.loginForm=this.fb.group({
      email:[null,[Validators.required,Validators.email]],
      password:[null,Validators.required]
    })
  }
  submit(){
    console.log(this.loginForm.value);
  }

}
