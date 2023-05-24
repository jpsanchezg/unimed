import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { RouterModule, Routes, Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent  implements OnInit {

  constructor(private router: Router) { }
  @Output() searchChange = new EventEmitter<string>();
  ngOnInit() {


  }
  nombrePersona = localStorage.getItem('nombrePersona');
  goToCitasMedicas(){
    alert("Citas medicas")
    this.router.navigate(['/citasmedicas'])
  }
  goToTusCasos(){
    this.router.navigate(['/casos'])
  }
  goToFiltros(){
    this.router.navigate(['/filtros'])
  }
  onSearch() {
    // Aquí puedes realizar la lógica de búsqueda utilizando el valor ingresado en la barra de búsqueda
    console.log("Realizando búsqueda: ");
  }
}
