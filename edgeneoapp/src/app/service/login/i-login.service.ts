import { HttpRequest } from "@angular/common/http";
import { BehaviorSubject } from "rxjs";
import { Usuario } from "../../model/usuario";
import { InjectionToken } from "@angular/core";

export interface ILoginService {
  usuarioAutenticado: BehaviorSubject<Usuario>;
  login(usuario: Usuario): void;
  logout(): void;
  isLoggedIn(): boolean;
  getHeaders(request: HttpRequest<any>): HttpRequest<any>;
}

export const LoginService = new InjectionToken<ILoginService>('ILoginService');
