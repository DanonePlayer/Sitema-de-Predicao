import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Aluno } from '../../model/aluno';
import { AlunoDisciplinas } from '../../model/alunoDisciplinas';

@Injectable({
  providedIn: 'root'
})
export class AlunoService {
  apiUrl: string = environment.API_URL + '/aluno';

  constructor(private http: HttpClient) {}

  cadastrarAluno(aluno: Aluno): Observable<Aluno> {
    return this.http.post<Aluno>(`${this.apiUrl}/`, aluno);
  }

  associarDisciplinasAoAluno(alunoDisciplinas: AlunoDisciplinas, usuarioId: number): Observable<any> {
    const url = `${this.apiUrl}/${usuarioId}/disciplinas`;
    return this.http.put<any>(url, alunoDisciplinas);
  }

  buscarDisciplinasPorAluno(usuarioId: number): Observable<any> {
    const url = `${this.apiUrl}/${usuarioId}/disciplinas`;
    return this.http.get<any>(url);
  }





  dados(): Observable<any> {
    const apiUrl = environment.API_URL + '/treinamento/weka/dados';
    return this.http.get(apiUrl);
  }

  prever(aluno: any): Observable<any> {
    const apiUrl = environment.API_URL + '/treinamento/weka/predict';
    return this.http.post(apiUrl, aluno);
  }



  // cadastrarAlunoComDisciplinas(alunoDisciplinas: AlunoDisciplinas): Observable<any> {
  //   return this.http.post(`${this.apiUrl}`, alunoDisciplinas);
  // }

  // buscarNomePorId(usuarioId: number): Observable<string> {
  //   return this.http.get<string>(`${this.apiUrl}${usuarioId}/nome`);
  // }

  // Outros métodos, como buscar, atualizar e deletar alunos, podem ser adicionados aqui
}
