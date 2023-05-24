import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NavbarComponent } from './navbar.component';
import { CitasmedicasComponent } from '../citasmedicas/citasmedicas.component';

const routes: Routes = [
  {
    path: 'home',
    component: NavbarComponent,
    children: [
      {
        path: 'home',
        loadChildren: () => import('../tab1/tab1.module').then(m => m.Tab1PageModule)
      },
      {
        path: 'citasmedicas',
        component: CitasmedicasComponent
      },
      {
        path: 'notifications',
        loadChildren: () => import('../tab3/tab3.module').then(m => m.Tab3PageModule)
      },
      {
        path: '',
        redirectTo: '/tabs/tab1',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
})
export class NavbarRoutingModule { }
