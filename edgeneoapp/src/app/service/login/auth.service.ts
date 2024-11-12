  import { isPlatformBrowser } from '@angular/common';
import { Token } from '@angular/compiler';
  import { Inject, inject, Injectable, PLATFORM_ID } from '@angular/core';
  import { Router } from '@angular/router';
  import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
  import { Observable } from 'rxjs/internal/Observable';

  @Injectable({
    providedIn: 'root'
  })
  export class AuthService {
    private logoutTimer: any;  // Temporizador para agendar o logout
    private router: Router = inject(Router);
    private erroSubject = new BehaviorSubject<number | null>(null);  // Aqui guardamos o status do erro

    constructor(@Inject(PLATFORM_ID) private platformId: Object) {}


    // Método para armazenar o token no localStorage
    setToken(token: string): void {
      if (isPlatformBrowser(this.platformId) && token) {
        localStorage.setItem('token', token);
      } else {
        console.warn('localStorage não está disponível no ambiente atual. O token não pôde ser armazenado.');
      }
    }

    // Método para recuperar o token do localStorage
    getToken(): string | null {
      if (isPlatformBrowser(this.platformId)) {
        return localStorage.getItem('token');
      }
      return null;
    }


    // Método para verificar se o usuário está autenticado
    isAuthenticated(): boolean {
      const token = this.getToken();
      if (token) {
        const tokenExp = this.getTokenExpirationDate(token);
        if (tokenExp) {
          return tokenExp > new Date();
        }
      }
      return false;
    }

    getEmailFromToken(token: string): string | null {
      const payload = this.decodeToken(token);
      return payload ? payload.email : null;
    }

    getNomeFromToken(token: string): string | null {
      const payload = this.decodeToken(token);
      return payload ? payload.nome : null;
    }

    getIdFromToken(token: string): number | null {
      const payload = this.decodeToken(token);
      return payload ? payload.id : null;
    }

    getUsuarioFromToken(token: string): any | null {
      const payload = this.decodeToken(token);
      if (payload) {
        return {
          id: payload.id,
          nome: payload.nome,
          email: payload.email,
          tipo: payload.tipo,
          dataLimiteRenovacao: payload.dataLimiteRenovacao,
        };
      }
      return null;
    }

    private isLocalStorageAvailable(): boolean {
      if (!isPlatformBrowser(this.platformId)) {
        return false;
      }
      try {
        const testKey = '__test__';
        localStorage.setItem(testKey, testKey);
        localStorage.removeItem(testKey);
        return true;
      } catch (e) {
        console.warn('localStorage indisponível, utilizando mock.');
        return false;
      }
    }



    private decodeToken(token: string): any | null {
      try {
        const payload = token.split('.')[1];
        const decodedPayload = atob(payload);
        return JSON.parse(decodedPayload);
      } catch (error) {
        console.error('Erro ao decodificar o token:', error);
        return null;
      }
    }

    // Obtém a data de expiração do token
    private getTokenExpirationDate(token: string): Date | null {
      const payload = this.decodeToken(token);
      if (payload && payload.exp) {
        const expirationDate = new Date(payload.exp * 1000);
        return expirationDate;
      }
      return null;
    }

    // Agendar o logout após um determinado período (por exemplo, 1 minuto)
    scheduleLogout(): void {
      this.clearLogoutTimer();
      const logoutDelayMilliseconds = 600 * 10000;
      const logoutDelayMinutes = Math.floor(logoutDelayMilliseconds / (60 * 1000));
      const logoutDelayHours = Math.floor(logoutDelayMinutes / 60);

      this.logoutTimer = setTimeout(() => {
        this.logout();
      }, logoutDelayMilliseconds);

      // Verificar se é maior que uma hora para exibir em horas e minutos
      if (logoutDelayHours > 0) {
        const remainingMinutes = logoutDelayMinutes % 60;
        console.log(`Logout agendado para ${logoutDelayHours} hora(s) e ${remainingMinutes} minuto(s).`);
      } else {
        console.log(`Logout agendado para ${logoutDelayMinutes} minuto(s).`);
      }
    }

    // Limpa o temporizador de logout se necessário
    clearLogoutTimer(): void {
      if (this.logoutTimer) {
        clearTimeout(this.logoutTimer);
        this.logoutTimer = null;
        console.log('Temporizador de logout limpo.');
      }
    }

    // Realiza o logout e limpa qualquer informação armazenada
    logout(): void {
      if (isPlatformBrowser(this.platformId)) {
        localStorage.removeItem('token');
      }
      this.clearLogoutTimer();  // Limpa o timer de logout
      console.log('Usuário deslogado.');
      this.router.navigate(['/login']);  // Redireciona para a tela de login
    }

    //A bomba pra armazenar o status do erro, de quem foi a ideia de fazer no service isso????????????
    setErro(status: number): void {
      this.erroSubject.next(status);
    }

    //a bomba pra pegar, ainda quero saber quem teve a ideia de n fazer no componenent o token e sim no service que n manda o erro🤣🤣🤣🤣🤣
    getErro(): Observable<number | null> {
      return this.erroSubject.asObservable();
    }
  }
