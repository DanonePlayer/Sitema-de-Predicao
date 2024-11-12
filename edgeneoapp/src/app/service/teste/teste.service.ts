import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TesteService {
  constructor(private http: HttpClient) {}

  realizarTeste(): Observable<any> {
    return this.http.post<any>('/api/treinar', {}); // Substitua pela URL real da API
  }
}
