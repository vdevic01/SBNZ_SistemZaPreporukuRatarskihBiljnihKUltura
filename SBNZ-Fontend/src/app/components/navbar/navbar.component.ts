import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthorizationService} from "../../core/services/authorization.service";

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(public router:Router, private authService: AuthorizationService) {
  }
  logout(){
    this.authService.logOut();
    this.router.navigate(['/']).then(() => {});
  }
}
