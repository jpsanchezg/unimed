import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Keyboard } from '@capacitor/keyboard';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit {

  constructor(private router: Router) { }

  iniciarSesion() {
    // Aquí puedes agregar el código para validar el inicio de sesión

    // Redirige a la pantalla de bienvenida
    this.router.navigate(['/tabs']);
  }

  ngOnInit() { }

}
