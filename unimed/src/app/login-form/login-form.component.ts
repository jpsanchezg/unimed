import { Component, OnInit } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';
import { Keyboard } from '@capacitor/keyboard';
import { IonicModule, IonicRouteStrategy } from '@ionic/angular';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {

  constructor(private router: Router) { }

  navigate() {
    this.router.navigate(['/home'])
  }

  ngOnInit() { }

}
