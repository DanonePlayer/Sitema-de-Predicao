// cadastro-pessoal.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { UsuarioService } from '../service/usuario/usuario.service';
import { AuthService } from '../service/login/auth.service';

@Injectable({
  providedIn: 'root'
})
export class cadastroPessoalGuard implements CanActivate {

  constructor(
    private usuarioService: UsuarioService,
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(): Observable<boolean> {
    if (!this.authService.isAuthenticated()) {
      console.log('Usuário não autenticado ou token expirado. Redirecionando para /login.');
      this.router.navigate(['/login']);
      return of(false);
    }

    const token = this.authService.getToken();
    // Usar o método getIdFromToken para obter o ID do usuário a partir do token
    const usuarioId = token ? this.authService.getIdFromToken(token) : null;

    if (!usuarioId) {
      this.router.navigate(['/login']);
      return new Observable<boolean>(observer => observer.next(false));
    }

    // Verificar se o cadastro pessoal foi concluído
    return this.usuarioService.isCadastroPessoalCompleto(usuarioId).pipe(
      map(completo => {
        if (completo) {
          console.log('Cadastro pessoal completo');
          this.router.navigate(['/cadastro-historico-escolar']);
          return false;
        }
        return true; // Permite acessar a rota de cadastro pessoal se ainda não estiver completo
      })
    );
  }
}
