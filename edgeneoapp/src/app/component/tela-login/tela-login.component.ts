import { Component, Inject } from "@angular/core";
import { FormsModule, NgForm } from "@angular/forms";
import { Router } from "@angular/router";
import { loginService } from "../../service/login/loginService";
import { CommonModule } from "@angular/common";
import { ETipo } from "../../model/ETipo";
import { ILoginService, LoginService } from "../../service/login/i-login.service";
import { Usuario } from "../../model/usuario";
import { AuthService } from "../../service/login/auth.service";

// Interface para representar as credenciais de login (LoginRequest)
interface LoginRequest {
  email: string;
  senha: string;
}

// Interface para representar a resposta de login (LoginResponse)
interface LoginResponse {
  id: number;
  email: string;
  tipo: ETipo; // ALUNO ou COORDENADOR
  cadastroPessoalCompleto?: boolean;
  cadastroHistoricoCompleto?: boolean;
  portariaAprovada?: boolean;
}

@Component({
  selector: "app-tela-login",
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: "./tela-login.component.html",
  styleUrls: ["./tela-login.component.css"]
})
export class TelaLoginComponent {
  // Propriedade para armazenar as credenciais de login
  loginRequest: LoginRequest = {
    email: "",
    senha: ""
  };

  erro: string = "";

  constructor(
    @Inject(LoginService) private servico: ILoginService,
    @Inject(Router) private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit()
  {
    this.authService.getErro().subscribe((status) =>
    {
      if (status === 401)
      {
        this.erro = "Email ou Senha incorretos";
      }

      else if (status !== null)
      {
        this.erro = "Que loucura você fez?";
      }
    });
  }

  usuario: Usuario = <Usuario>{};

  logar(form: NgForm): void
  {
    if (form.valid) {
      this.servico.login(this.usuario);
      form.resetForm();
    }
  }

  // Método para realizar o login
  // logar(form: NgForm) {
  //   if (form.valid) {

  //     console.log("Credenciais fornecidas:", this.loginRequest);  // Verifica o objeto enviado
  //     console.log("Enviando requisição para URL:", this.loginService.apiUrl);

  //     // Chama o serviço de login passando as credenciais como parâmetro
  //     this.loginService.login(this.loginRequest.email, this.loginRequest.senha).subscribe(
  //       (response: LoginResponse) => {
  //         console.log("Login bem-sucedido!", response);

  //         localStorage.setItem('usuarioId', response.id.toString());

  //         this.redirecionarUsuario(response);
  //       },
  //       error => {
  //         console.error("Erro ao fazer login:", error);
  //         this.erro = "Usuário ou Senha inválidos";
  //       }
  //     );
  //   }
  // }

  // Método para redirecionar o usuário com base no status de login
//   redirecionarUsuario(usuario: Usuario): void {
//   // Recupera o tipo do usuário autenticado
//   const tipoUsuario = usuario.tipo;

//   switch (tipoUsuario) {
//     case 'ALUNO':
//       if (!usuario.cadastroPessoalCompleto) {
//         // Cenário 1: Aluno que não cadastrou dados pessoais
//         this.router.navigate(['/cadastro_aluno']);
//       } else if (!usuario.cadastroHistoricoCompleto) {
//         // Cenário 2: Aluno que não cadastrou dados do histórico escolar
//         this.router.navigate(['/cadastro_materias']);
//       } else {
//         // Cenário 3: Aluno que cadastrou tudo corretamente
//         this.router.navigate(['/home_aluno']);
//       }
//       break;

//     case 'COORDENADOR':
//       if (!usuario.portariaAprovada) {
//         // Cenário 4: Coordenador que não teve portaria aprovada
//         this.router.navigate(['/coordenador_espera']);
//       } else {
//         // Cenário 5: Coordenador com portaria aprovada
//         this.router.navigate(['/home_coordenador']);
//       }
//       break;

//     case 'ADMINISTRADOR':
//       // Novo Cenário: Redireciona para a tela home do administrador
//       this.router.navigate(['/home_administrador']);
//       break;

//     default:
//       // Caso não seja nenhum dos tipos esperados, redirecionar para uma rota padrão (exemplo: página de login)
//       this.router.navigate(['/login']);
//       break;
//   }
// }

}
