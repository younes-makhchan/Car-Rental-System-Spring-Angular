import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { NzMessageService } from 'ng-zorro-antd/message';
import { Router } from '@angular/router';
import { StorageService } from '../../storage/storage.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginForm!:FormGroup;
  constructor(
    private fb:FormBuilder,
    private authService:AuthService,
    private router:Router,
    private message:NzMessageService
    ) {    
     }
  ngOnInit(): void {
    this.loginForm=this.fb.group({
      email:[null,[Validators.required,Validators.email]],
      password:[null,Validators.required]
    })
  }
  submit(){
    //need to handle the erro
    this.authService.login(this.loginForm.value).subscribe(
      (res:any)=>{
        console.log(res);
        if(res.userId!=null){
          const user={
            id:res.userId,
            role:res.userRole
          }
          StorageService.saveToken(res.jwt);
          StorageService.saveUser(user);
          if(StorageService.isAdminLoggedIn()){
            this.router.navigate(["/admin/dashboard"])

          }else if(StorageService.isCustomerLoggedIn()){
            this.router.navigate(["/customer/dashboard"])
          }else{
            this.message.error("Bad credentials",{nzDuration:5000});
          }
        }
      }
    )
  }

}
