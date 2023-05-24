import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { RegisterComponent } from './register/register.component';
import { HomeComponent } from './home/home.component';
import { CitasmedicasComponent } from './citasmedicas/citasmedicas.component';
import { FiltrosComponent } from './filtros/filtros.component';
import { CasosComponent } from './casos/casos.component';

const routes: Routes = [
  // { path: '', loadChildren: './tabs/tabs.module#TabsPageModule' },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginFormComponent },
  { path: 'registro', component: RegisterComponent },

  { path: 'home', component: HomeComponent },
  { path: 'citasmedicas', component: CitasmedicasComponent },
  { path: 'filtros', component: FiltrosComponent },
  { path: 'casos', component: CasosComponent },
];
@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
