import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdministradorServiceService {
  apiUrl: string = environment.API_URL + '/administrador/'

  constructor(private http: HttpClient) {}


  treinar(): Observable<any> {
    const apiUrl = environment.API_URL + '/treinamento/weka/train';
    return this.http.get(apiUrl);
  }
}
