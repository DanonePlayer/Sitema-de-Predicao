import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Coordenador } from '../../model/coordenador';

@Injectable({
  providedIn: 'root'
})
export class CoordenadorService {
  apiUrl: string = environment.API_URL + '/coordenador/'

  constructor(private http: HttpClient) {}

  getCoordenadores(): Observable<Coordenador[]> {
    return this.http.get<Coordenador[]>(`${this.apiUrl}`);
  }

  getCoordenadorById(coordenadorId: number): Observable<Coordenador> {
    return this.http.get<Coordenador>(`${this.apiUrl}${coordenadorId}`);
  }


  cadastrarCoordenador(coordenador: Coordenador): Observable<any> {
    return this.http.post(`${this.apiUrl}`, coordenador);
  }

  uploadPortaria(coordenadorId: number, formData: FormData): Observable<any> {
    const uploadUrl = `${this.apiUrl}${coordenadorId}/upload-portaria`;
    return this.http.post(uploadUrl, formData);
  }

  baixarPortaria(coordenadorId: number): Observable<Blob> {
    const downloadUrl = `${this.apiUrl}${coordenadorId}/downloadPortaria`;
    return this.http.get(downloadUrl, { responseType: 'blob' }); // 'blob' para lidar com arquivos binários
  }

  // Método para atualizar um coordenador existente
  atualizarCoordenador(coordenador: Coordenador): Observable<Coordenador> {
    return this.http.put<Coordenador>(`${this.apiUrl}${coordenador.id}`, coordenador);
  }

  // Método para excluir um coordenador
  excluirCoordenador(coordenadorId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}${coordenadorId}`);
  }

// sera trocado para teste depois
  // treinar(): Observable<any> {
  //   const apiUrl = environment.API_URL + '/treinamento/weka/train';
  //   return this.http.get(apiUrl);
  // }

  treinar(): Observable<any> {
    const apiUrl = environment.API_URL + '/treinamento/weka/train';
    return this.http.get(apiUrl, { responseType: 'json' });  // Certifique-se de que a resposta é tratada como JSON
  }
  
}
