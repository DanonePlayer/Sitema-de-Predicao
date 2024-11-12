import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../../model/usuario';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {
  apiUrl: string = environment.API_URL + '/usuario/';

  constructor(private http: HttpClient) {}

  // cadastrar(usuario: Cadastro){
  //   return this.http.post(`${this.apiUrl}`, usuario);
  // }

  cadastrar(usuario: Usuario): Observable<any> {
    return this.http.post(`${this.apiUrl}`, usuario);
  }

  verificarEmail(email: string)
  {
    return this.http.get<boolean>(`${this.apiUrl}/verificar-email?email=${email}`);
  }

  confirmarEmail(token: string): Observable<any> {
    return this.http.get(`${this.apiUrl}confirmacao-email?token=${token}`, { responseType: 'text' });
  }
}
