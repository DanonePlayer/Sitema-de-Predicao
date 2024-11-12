import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CepService {

  private readonly viaCepUrl = 'https://viacep.com.br/ws';  // URL base da API ViaCEP

  constructor(private http: HttpClient) {}

  // Método para buscar endereço por CEP
  buscarEnderecoPorCep(cep: string): Observable<any> {
    // Remover caracteres não numéricos do CEP
    cep = cep.replace(/\D/g, '');
    // Verificar se o CEP possui 8 dígitos
    if (cep.length !== 8) {
      throw new Error('CEP inválido. Deve conter 8 dígitos numéricos.');
    }
    // Retorna a requisição GET para a API ViaCEP
    return this.http.get(`${this.viaCepUrl}/${cep}/json/`);
  }
}
