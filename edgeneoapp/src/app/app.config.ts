import { ApplicationConfig } from '@angular/core';
import { provideHttpClient, HTTP_INTERCEPTORS, withInterceptors, withFetch } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { authInterceptor } from './interceptor/auth.interceptor';
import { LoginService } from './service/login/i-login.service';
import { loginService } from './service/login/loginService';
import { erroInterceptor } from './interceptor/erro.interceptor';

export function loginServiceFactory() {
  const authType = 'jwt';
  return new loginService();
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([authInterceptor, erroInterceptor]), withFetch()),
    { provide: LoginService, useFactory: loginServiceFactory }
  ]
};
