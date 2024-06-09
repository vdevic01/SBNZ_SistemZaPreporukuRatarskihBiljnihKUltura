import { Injectable } from '@angular/core';
import {AccessTokenResponse, LoginCredentials} from "../model/authorization";
import {BehaviorSubject, Observable} from "rxjs";
import {environment} from "../../environment";
import {HttpClient} from "@angular/common/http";
import {JwtHelperService} from "@auth0/angular-jwt";

@Injectable({
  providedIn: 'root'
})
export class AuthorizationService {
  user: BehaviorSubject<any | undefined> = new BehaviorSubject<any | undefined>(undefined);
  constructor(private http: HttpClient) {
    this.loadUser();
  }
  loadUser(){
    const helper = new JwtHelperService();
    const token:string | null = localStorage.getItem("authToken");
    if(token == null){
      this.user.next(undefined);
      return;
    }
    const decodedToken = helper.decodeToken(token);
    const id:number = decodedToken.id;
    const email:string = decodedToken.email;
    this.user.next({userId: id, userEmail: email});
  }
  loginUser(credentials:LoginCredentials):Observable<AccessTokenResponse>{
    return this.http.post<AccessTokenResponse>(`${environment.apiHost}/login`, credentials)
  }
  logOut(){
    localStorage.clear();
    this.user.next(undefined);
  }
}
