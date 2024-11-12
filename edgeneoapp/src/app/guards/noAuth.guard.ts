import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { AuthService } from '../service/login/auth.service';

@Injectable({
  providedIn: 'root'
})
export class NoAuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean> {
    // Verificar se o usuário está autenticado
    if (this.authService.isAuthenticated()) {
      const token = this.authService.getToken();
      console.log('Token: ' + token + ' encontrado no localStorage.');
      console.log('Usuário já está autenticado. Redirecionando para a rota inicial.');

      // Redirecionar para a rota inicial (ajuste conforme a necessidade)
      // this.router.navigate(['/home_aluno']);            // arrumar ainda
      return of(false);  // Bloqueia o acesso às páginas de login e cadastro
    }

    return of(true);  // Permite o acesso às páginas de login e cadastro se não estiver autenticado
  }
}
