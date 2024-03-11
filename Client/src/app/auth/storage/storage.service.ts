import { Injectable } from '@angular/core';

const Token="token";
const USER="user";
@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }
  static saveToken(token:string):void{
    window.localStorage.removeItem(Token);
    window.localStorage.setItem(Token,token);
  }
  static saveUser(user:any):void{
    window.localStorage.removeItem(USER);
    window.localStorage.setItem(USER,JSON.stringify(user));
  }
  static getToken():string|null{
    return window.localStorage.getItem(Token);
  }
  static getUser(){
    return JSON.parse(localStorage.getItem(USER)||"null");
    
  }
  static getUserRole(){
    const user=this.getUser();
    if(user==null)return "";
    return user.role;
  }
  static isAdminLoggedIn():boolean{
    if(this.getToken()==null)return false;
    const role:string=this.getUserRole();
    return role=="ADMIN";
  }
  static isCustomerLoggedIn():boolean{
    if(this.getToken()==null)return false;
    const role:string=this.getUserRole();
    return role=="CUSTOMER";
  }
  static logout(){
    window.localStorage.removeItem(Token);
    window.localStorage.removeItem(USER);
  }
}
