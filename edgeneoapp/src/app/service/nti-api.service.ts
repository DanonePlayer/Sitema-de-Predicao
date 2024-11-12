import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NtiApiService {
  apiUrl: string = environment.API_URL + '/nti-api'

  constructor(private http: HttpClient) { }

  // Método para iniciar a requisição de dados de alunos
  obterDadosAlunos(anoIngresso: number, codCurso: string): Observable<string> {
    const requestBody = {
      ANO_INGRESSO: anoIngresso,
      COD_CURSO: codCurso
    };
    return this.http.post<string>(`${this.apiUrl}/dados-alunos`, requestBody);
  }

  // Mantém o retorno como Observable<string> para capturar a resposta correta
  buscarNomeCurso(codCurso: string, anoIngresso: number): Observable<string> {
    return this.http.get(`${this.apiUrl}/buscar-nome-curso`, {
      params: {
        codCurso: codCurso,
        anoIngresso: anoIngresso.toString()
      },
      responseType: 'text' // Define o tipo da resposta como texto
    });
  }


}
