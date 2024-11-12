// cadastro-historico.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { UsuarioService } from '../service/usuario/usuario.service';
import { AuthService } from '../service/login/auth.service';

@Injectable({
  providedIn: 'root'
})
export class cadastroHistoricoGuard implements CanActivate {

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

    return this.usuarioService.isCadastroPessoalCompleto(usuarioId).pipe(
      switchMap(cadastroPessoalCompleto => {
        if (!cadastroPessoalCompleto) {
          console.log('Cadastro pessoal não está completo. Redirecionando para /cadastro_aluno.');
          this.router.navigate(['/cadastro_aluno']);
          return of(false);  // Bloqueia o acesso se o cadastro pessoal não estiver completo
        }

    // Verificar se o cadastro do histórico escolar foi concluído
    return this.usuarioService.isCadastroHistoricoCompleto(usuarioId).pipe(
      map(completo => {
        if (completo) {
          this.router.navigate(['/dashboard']);
          return false;
        }
        return true; // Permite acessar a rota de cadastro de histórico se ainda não estiver completo
        })
      );
    }));
  }
}
