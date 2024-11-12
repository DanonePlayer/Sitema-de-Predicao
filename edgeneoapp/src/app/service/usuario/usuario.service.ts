import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Usuario } from '../../model/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http: HttpClient) {}
  apiUrl: string = environment.API_URL + '/usuario'
  // Verificar se o cadastro pessoal foi concluído

  getUsuario(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}`);
  }

  getUsuarioById(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${id}`);
  }

  // Atualizar um usuário
  atualizarUsuario(usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${usuario.id}`, usuario);
  }

  isCadastroPessoalCompleto(id: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/cadastro-pessoal-completo/${id}`);
  }

  // Verificar se o cadastro do histórico escolar foi concluído utilizando o ID do usuário
  isCadastroHistoricoCompleto(id: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/cadastro-historico-completo/${id}`);
  }

}
