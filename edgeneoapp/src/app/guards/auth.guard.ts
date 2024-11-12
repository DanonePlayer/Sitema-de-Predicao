import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../service/login/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthenticated()) {
    return true;  // Permite o acesso
  } else {
    router.navigate(['/login']);  // Redireciona para a página de login
    return false;  // Bloqueia o acesso
  }
};
