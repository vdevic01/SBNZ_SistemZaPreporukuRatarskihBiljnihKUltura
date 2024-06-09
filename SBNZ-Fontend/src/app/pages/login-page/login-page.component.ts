import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {NgIf} from "@angular/common";
import {AuthorizationService} from "../../core/services/authorization.service";
import {LoginCredentials} from "../../core/model/authorization";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-login-page',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.css'
})
export class LoginPageComponent {
  errorMessage: string = '';
  loginForm:FormGroup;
  constructor(private router:Router, private authService: AuthorizationService) {
    this.loginForm = new FormGroup({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required])
    });
  }
  submitForm(){
    this.errorMessage = '';
    if(this.loginForm.valid){
      const credentials:LoginCredentials = {
        email: this.loginForm.controls['email'].value,
        password: this.loginForm.controls['password'].value
      }
      this.login(credentials);
    }
  }
  private login(credentials:LoginCredentials){
    this.authService.loginUser(credentials).subscribe({
      next: (response) => {
        localStorage.setItem('authToken', response.token);
        this.authService.loadUser();
        this.router.navigate(['/home']).then(() => {});
      },
      error: (error) => {
        if(error instanceof HttpErrorResponse){
          this.errorMessage = error.error.message;
        }
      }
    });
  }
}
