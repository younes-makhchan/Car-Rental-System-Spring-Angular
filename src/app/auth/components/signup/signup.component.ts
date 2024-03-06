import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit{
  isSpinning:boolean =false;
  signupForm!:FormGroup;
  constructor(private fb:FormBuilder) { }
  ngOnInit(): void {
    this.signupForm=this.fb.group({
      name:[null,Validators.required],
      email:[null,[Validators.required,Validators.email]],
      password:[null,[Validators.required,this.passwordPaternValidator]],
      confirmPassword:[null,[Validators.required,this.confirmationValidator]]
    })
  }
  confirmationValidator=(control:FormControl):{[s:string]:boolean}=>{

    if(!control.value){
      return {required:true};
    }else if(control.value !== this.signupForm.controls['password'].value){
      return {confirm:true,error:true}

    }
    return {}
  }
  passwordPaternValidator=(control:FormControl):{[s:string]:boolean}=>{
    const password: string = control.value;
    if(!control.value){
      return {required:true};
    }
     // Regular expressions to check for each required character type
     const letterRegex = /[a-zA-Z]/;
     const uppercaseRegex = /[A-Z]/;
     const lowercaseRegex = /[a-z]/;
     const numberRegex = /[0-9]/;
     const specialCharRegex = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
    
     const errors:{[s:string]:boolean} = {};
     // Check if password contains 8 characters
     if (password.length < 8) {
      errors['invalidLength'] = true;
    }

    // Check if password contains at least one uppercase letter
    if (!uppercaseRegex.test(password)) {
      errors['missingUppercase'] = true;
    }

    // Check if password contains at least one lowercase letter
    if (!lowercaseRegex.test(password)) {
      errors['missingLowercase'] = true;
    }

    // Check if password contains at least one number
    if (!numberRegex.test(password)) {
      errors['missingNumber'] = true;
    }

    // Check if password contains at least one special character
    if (!specialCharRegex.test(password)) {
      errors['missingSpecialChar'] = true;
    }
    return errors;
  }

  submit(){
    console.log(this.signupForm.value);
  }
}
