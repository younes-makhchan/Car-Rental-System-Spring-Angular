import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASE_URL=["http://localhost:9000"]//change to env
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }
  //normal signuprequest should be an interface
  register(signUpRequest:any):Observable<any>{
    return this.http.post(`${BASE_URL}/api/auth/signup`,signUpRequest)
  }
}
