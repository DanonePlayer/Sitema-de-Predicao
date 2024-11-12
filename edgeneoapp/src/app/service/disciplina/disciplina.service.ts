import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Disciplina } from '../../model/disciplina';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DisciplinaService {
  apiUrl: string = environment.API_URL + '/disciplina/';

  constructor(private http: HttpClient) {}

  // Método para pegar todas as disciplinas
  getAllDisciplinas(): Observable<Disciplina[]> {
    return this.http.get<Disciplina[]>(`${this.apiUrl}`);
  }
}
