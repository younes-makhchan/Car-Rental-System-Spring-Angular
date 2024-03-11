import { Component, OnInit } from '@angular/core';
import { StorageService } from './auth/storage/storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'car_rental_angular';
  isCustomerLoggedIn:boolean=StorageService.isCustomerLoggedIn();
  isAdminLoggedIn:boolean=StorageService.isAdminLoggedIn();
  constructor(private router:Router){

  }
  ngOnInit(){
    //improve logic here 
    this.router.events.subscribe(event=>{
      if(event.constructor.name==="NavigationEnd"){
        this.isAdminLoggedIn=StorageService.isAdminLoggedIn();
        this.isCustomerLoggedIn=StorageService.isCustomerLoggedIn();
        console.log(this.isAdminLoggedIn,this.isCustomerLoggedIn)
      }
    })
  }
  logout(){
    StorageService.logout();
    this.router.navigate(["/login"]);
  } 
}
