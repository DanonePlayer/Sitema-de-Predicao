import { AfterViewInit, ChangeDetectorRef, Component, Inject, PLATFORM_ID } from '@angular/core';
import { CoordenadorService } from '../../../service/coordenador/coordenador.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tela-testes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './tela-testes.component.html',
  styleUrl: './tela-testes.component.css'
})
export class TelaTestesComponent implements AfterViewInit {
  exibindoTeste: boolean = true;
  exibindoDashboard: boolean = true
  dadosTeste: any = null;
  periodoSelecionado: string = "Período 7";

  constructor(
    // private testeService: TesteService,
    private coordenadorService: CoordenadorService,
    private router: Router,
    @Inject(PLATFORM_ID) private platformId: Object,
  ) { }
  
  ngAfterViewInit(): void {
    console.log("ngAfterViewInit executado");
  }

  selecionarPeriodo(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.periodoSelecionado = selectElement.value;
  }
  

  realizarTeste(): void {
    this.coordenadorService.treinar().subscribe(
      (response: any) => {
        console.log(response);
        this.dadosTeste = response;
        // Navega para o dashboard após o teste ser realizado
        this.router.navigate(['/home_coordenador'], { queryParams: { dados: JSON.stringify(response) } });
      },
      error => {
        console.error('Erro ao treinar:', error);
      }
    );
  }
}
