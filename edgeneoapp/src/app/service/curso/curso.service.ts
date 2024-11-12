import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Curso } from '../../model/curso';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CursoService {
  private apiUrl: string = environment.API_URL + '/curso/';

  constructor(private http: HttpClient) {}

  // Método para buscar todos os cursos no backend
  listarCursos(): Observable<Curso[]> {
    return this.http.get<Curso[]>(`${this.apiUrl}`);
  }

  // Método para buscar o curso pelo ID
  getCursoById(curso_id: number): Observable<Curso> {
    return this.http.get<Curso>(`${this.apiUrl}${curso_id}`);
  }
}
