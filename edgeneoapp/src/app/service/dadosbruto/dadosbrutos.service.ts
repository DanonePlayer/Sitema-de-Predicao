import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DadosBrutos } from '../../model/dadosBrutos';

@Injectable({
  providedIn: 'root'
})
export class DadosbrutosService {
  apiUrl: string = environment.API_URL + '/dados-brutos/'

  constructor(private http: HttpClient) { }

  get(termoBusca?: string): Observable<DadosBrutos[]> {
    return this.http.get<DadosBrutos[]>(`${this.apiUrl}`)
  }
  getById(id: number): Observable<DadosBrutos> {
    throw new Error('Method not implemented.');
  }
  save(objeto: DadosBrutos): Observable<DadosBrutos> {
    throw new Error('Method not implemented.');
  }
  delete(id: number): Observable<void> {
    throw new Error('Method not implemented.');
  }
}
