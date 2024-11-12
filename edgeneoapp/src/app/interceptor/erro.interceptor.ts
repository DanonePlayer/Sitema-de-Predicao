import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AlertaService } from '../service/alerta.service';
import { LoginService } from '../service/login/i-login.service';
import { catchError, throwError } from 'rxjs';
import { ETipoAlerta } from '../model/e-tipo-alerta';

const ERRO_HTTP: Record<number, string> = {
  401: "Não autorizado",
  403: "Acesso proibido",
  404: "Recurso não encontrado",
  500: "Erro interno do servidor"
}

export const erroInterceptor: HttpInterceptorFn = (req, next) => {
  const servicoAlerta = inject(AlertaService);
  const servicoLogin = inject(LoginService);

  return next(req).pipe(
    catchError(erro => {
      let mensagemErro = ERRO_HTTP[erro.status] || erro.error?.message || "Falha na requisição";

      if (erro.status === 401) {
        // Deslogar o usuário e evitar loop de requisições
        if (!req.url.includes('/login') && !req.url.includes('/confirmacao-email'))  {  // Certifique-se de que não está deslogando na página de login
          servicoLogin.logout(); // Realiza o logout somente se não for login ou confirmação de email
        }
      }

      // Exibe alerta com mensagem de erro
      servicoAlerta.enviarAlerta({
        tipo: ETipoAlerta.ERRO,
        mensagem: mensagemErro
      });

      // Retorna o erro para o fluxo de observadores
      return throwError(() => erro);
    })
  );
};
