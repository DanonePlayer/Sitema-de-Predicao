import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpRequest } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Usuario } from '../../model/usuario';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { AuthService } from './auth.service';
import { ILoginService } from './i-login.service';
import { text } from 'stream/consumers';
// // Interface para representar o objeto LoginRequest (contendo apenas email e senha)
// interface LoginRequest {
//   email: string;
//   senha: string;
// }

// // Interface para representar a resposta de login (LoginResponse)
// interface LoginResponse {
//   id: number;
//   email: string;
//   tipo: ETipo; // ALUNO ou COORDENADOR
//   cadastroPessoalCompleto?: boolean;
//   cadastroHistoricoCompleto?: boolean;
//   portariaAprovada?: boolean;
// }

@Injectable({
  providedIn: 'root'
})
export class loginService implements ILoginService{
  constructor() {}
  private http: HttpClient = inject(HttpClient);
  private router: Router = inject(Router);
  private authService: AuthService = inject(AuthService);

  usuarioAutenticado = new BehaviorSubject<Usuario>(<Usuario>{});

  login(usuario: Usuario): void {
    const url = environment.API_URL + '/login';

    this.http.post<string>(url, usuario, { responseType: 'text' as 'json' }).subscribe({
      next: (token: string) => {
        // Configura a sessão do usuário com o token recebido
        this.configurarSessaoUsuario(token);
        // Busca os detalhes do usuário autenticado usando o email
        const email = this.authService.getEmailFromToken(token);

        if (email)
        {
          // Chama o método buscarUsuarioDetalhado somente se o email não for nulo
          this.buscarUsuarioDetalhado(email);
        } else
        {
          console.error("Erro: Email não encontrado no usuário autenticado.");
        }
      },
      error: (error) => {
        console.log('Erro durante o login:', error);
        this.authService.setErro(error.status);  // Salva o status do erro no AuthService para o componente verificar
      },
      complete: () => {
        console.log('Login e configuração de sessão concluídos.');
      }
    });
  }


  private configurarSessaoUsuario(token: string) {
    // Define o token no AuthService para que ele possa ser usado nas próximas requisições
    this.authService.setToken(token);

    const usuario = this.authService.getUsuarioFromToken(token);
    if (usuario) {
      this.usuarioAutenticado.next(usuario);
      this.authService.scheduleLogout(); // Agendar o logout automático
    } else {
      console.error('Erro ao configurar a sessão do usuário a partir do token.');
    }
  }


  isLoggedIn(): boolean {
    return this.authService.isAuthenticated();
  }


  getHeaders(request: HttpRequest<any>): HttpRequest<any> {
    if (this.isLoggedIn()) {
      const token = this.authService.getToken();
      return request.clone({
        withCredentials: true,
        headers: request.headers.set('Authorization', 'Bearer ' + token)
      });
    }
    return request;
  }

  getUsuarioAutenticado(): Usuario {
    return this.usuarioAutenticado.value;  // Retorna o usuário autenticado armazenado no BehaviorSubject
  }

  // Redireciona o usuário após o login com base no tipo
  private redirecionarUsuarioAutenticado(): void {
    const usuario = this.usuarioAutenticado.value;  // Obtém o usuário autenticado do BehaviorSubject
    if (!usuario || !usuario.tipo) {
      // Se o usuário ou tipo for inválido, redirecionar para a tela de login
      this.router.navigate(['/login']);
      return;
    }

    switch (usuario.tipo) {
      case 'ALUNO':
        if (!usuario.cadastroPessoalCompleto) {
          this.router.navigate(['/cadastro_aluno']);
        } else if (!usuario.cadastroHistoricoCompleto) {
          this.router.navigate(['/cadastro_materias']);
        } else {
          this.router.navigate(['/home_aluno']);
        }
        break;

      case 'COORDENADOR':
        if (!usuario.portariaAprovada) {
          this.router.navigate(['/coordenador_espera']);
        } else {
          this.router.navigate(['/home_coordenador']);
        }
        break;

      case 'ADMINISTRADOR':
        this.router.navigate(['/home_administrador']);
        break;

      default:
        this.router.navigate(['/login']);
        break;
    }
  }

  private buscarUsuarioDetalhado(email: string): void {
    this.getUsuarioLogado(email).subscribe({
      next: (usuarioCompleto: Usuario) => {
        // Atualiza o BehaviorSubject com o usuário completo (incluindo dados adicionais)
        this.usuarioAutenticado.next(usuarioCompleto);

        // Redireciona o usuário com base no status e permissões
        this.redirecionarUsuarioAutenticado();
      },
      error: (error) => {
        console.error('Erro ao buscar detalhes do usuário:', error);
        // Em caso de erro, redireciona para a página de login
        this.router.navigate(['/login']);
      },
      complete: () => {
        console.log('Busca de detalhes do usuário concluída.');
      }
    });
  }

  getUsuarioLogado(email: string): Observable<Usuario> {
    const url = `${environment.API_URL}/usuario/me?email=${email}`;
    return this.http.get<Usuario>(url);
  }

  logout(): void {
    const token = this.authService.getToken();  // Recupera o token do AuthService

    if (!token) {
        console.error("Token de autenticação não encontrado.");

    }

    const url = `${environment.API_URL}/usuario/logout`;  // URL do endpoint de logout no backend

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // const headers = new HttpHeaders({
    //     'Authorization': `Bearer ${token}`,  // Adiciona o token no cabeçalho da requisição
    //     'Content-Type': 'application/json',

    // });

    // this.authService.logout();  // Remove o token e limpa os dados no frontend
    // this.usuarioAutenticado.next(<Usuario>{});  // Reseta o estado do BehaviorSubject
    // Faz a requisição POST para o logout no backend
    try {
      this.http.post(url, '', { headers, responseType: 'text'}).subscribe({

          complete: () => {
              window.alert('Logout realizado com sucesso no backend.');
              this.authService.logout();  // Remove o token e limpa os dados no frontend
              this.usuarioAutenticado.next(<Usuario>{});  // Reseta o estado do BehaviorSubject
          },
          error: (error) => {
              console.error('Erro ao realizar logout no backend:', error);
          }
      });
    } catch (error) {
      window.alert('Erro ao realizar logout no backend.');
    }
  }

  //Esse método abaixo não está funcionando direito
  // Método para realizar login
  // login(email: string, senha: string): Observable<LoginResponse> {
  //   const loginRequest: LoginRequest = { email, senha };
  //   console.log("Credenciais corretas enviadas:", loginRequest);

  //   const headers = new HttpHeaders({
  //     'Content-Type': 'application/json',
  //     'Accept': 'application/json'
  //   });

  //   return new Observable(observer => {
  //     this.http.post<any>(this.apiUrl,loginRequest ,{ headers }).subscribe(
  //       (response) => {
  //         console.log("Resposta recebida do backend:", response);
  //         const token = response['token'];
  //         if (token) {
  //           this.authService.setToken(token);  // Armazena o token no AuthService
  //           observer.next(response);
  //         } else {
  //           observer.error('Token não recebido na resposta.');
  //         }
  //       },
  //       (error) => {
  //         console.error("Erro ao fazer login no backend:", error);
  //         observer.error(error);
  //       }
  //     );
  //   });
  // }

  // Método para verificar se o usuário está logado
  // isLoggedIn(): boolean {
  //   const userData = sessionStorage.getItem('usuario') || '{}';
  //   const usuario = JSON.parse(userData);
  //   return Object.keys(usuario).length > 0;
  // }
  // // Método para logout
  // logout(): void {
  //   sessionStorage.removeItem('usuario');
  //   this.usuarioAutenticado.next(null);
  //   this.router.navigate(['/login']);
  // }

}
