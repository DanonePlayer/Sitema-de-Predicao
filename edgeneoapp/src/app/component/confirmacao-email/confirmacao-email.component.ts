import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CadastroService } from '../../service/cadastro/cadastro.service';

@Component({
  selector: 'app-confirmacao-email',
  standalone: true,
  imports: [],
  template: `
    <div *ngIf="mensagem">{{ mensagem }}</div>
  `,
  templateUrl: './confirmacao-email.component.html',
  styleUrl: './confirmacao-email.component.css'
})
export class ConfirmacaoEmailComponent implements OnInit {
  mensagem: string = '';

  constructor(
    private route: ActivatedRoute,
    private cadastroService: CadastroService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const token = params['token'];
      if (token) {
        this.cadastroService.confirmarEmail(token).subscribe(
          response => {
            this.mensagem = 'Email confirmado com sucesso! Agora você pode fazer login.';
            setTimeout(() => this.router.navigate(['/login']), 3000); // Redireciona após 3 segundos
          },
          error => {
            this.mensagem = 'O link de confirmação é inválido ou expirou.';
          }
        );
      }
    });
  }
}
