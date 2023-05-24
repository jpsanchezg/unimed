import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isLoginPage: boolean = true;
  isRegisterPage: boolean = true;
  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.isLoginPage = event.url.includes('login');
        this.isRegisterPage = event.url.includes('registro');
      }
    });
  }

  ngOnInit() { }
  goToCitasMedicas(){
    this.router.navigate(['/home'])
  }
  goToHome() {
    this.router.navigate(['/home'])
  }
}
