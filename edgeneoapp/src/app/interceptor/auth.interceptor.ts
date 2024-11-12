// auth.interceptor.ts
import { inject } from '@angular/core';
import { HttpInterceptorFn } from '@angular/common/http';
import { AuthService } from '../service/login/auth.service';
import { HttpRequest, HttpHandler } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  // Obter o token de autenticação do AuthService
  const token = authService.getToken();

  if (token) {
    // Clonar a requisição e adicionar o cabeçalho Authorization com o token
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    // console.log('Requisição com token:', clonedRequest);
    return next(clonedRequest);
  }

  // Se não houver token, continuar a requisição original
  return next(req);
};
