import { CommonModule, isPlatformBrowser } from '@angular/common';
import { ChangeDetectorRef, Component, HostListener, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { loginService } from '../../service/login/loginService';
import { CoordenadorService } from '../../service/coordenador/coordenador.service';
import { TelaTestesComponent } from './tela-testes/tela-testes.component';
import { FormsModule } from '@angular/forms';
import { TelaDashboardCoordComponent } from './tela-dashboard-coord/tela-dashboard-coord.component';

@Component({
  selector: 'app-tela-home-coordenador',
  standalone: true,
  imports: [
    FormsModule, 
    CommonModule,
    TelaDashboardCoordComponent,
    TelaTestesComponent
  ],
  templateUrl: './tela-home-coordenador.component.html',
  styleUrls: ['./tela-home-coordenador.component.css'] // Correção aqui
})
export class TelaHomeCoordenadorComponent implements OnInit {

  configON = false;
  exibindoTeste: boolean = false;
  mostrarDashboard: boolean = true
  dadosTeste: any = null;
  periodoSelecionado: string = "Período 7";
  // private chartInstance: Chart | null = null; // Guardar a instância do gráfico
  semestresVisiveis = false;
  disciplinasVisiveis = false;

  constructor(
    private loginService: loginService,
    private coordenadorService: CoordenadorService,
    private cdr: ChangeDetectorRef,
    @Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit(): void {

    // this.getdados();
    // this.preverSituacao();
  }

  mostrarSemestre(): void {
    if (this.semestresVisiveis) {
      this.semestresVisiveis = false;
    } else {
      this.semestresVisiveis = true;
    }
  }

  mostrarDisciplinas(): void {
    if (this.disciplinasVisiveis) {
      this.disciplinasVisiveis = false;
    } else {
      this.disciplinasVisiveis = true;
    }
  }

  // realizarTeste(): void {
  //   this.coordenadorService.treinar().subscribe(
  //     (response: any) => {
  //       console.log(response);
  //       this.dadosTeste = response;
  //     },
  //     error => {
  //       console.error('Erro ao treinar:', error);
  //     }
  //   );
  // }

  // Filtro para o coordenador fazer os testes depois
  selecionarPeriodo(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.periodoSelecionado = selectElement.value; // Atualiza o período selecionado com o valor do select
  }

  configs() {
    this.configON = !this.configON;
  }

  @HostListener('document:click', ['$event'])
  fechaconfigs(event: Event) {
    const target = event.target as HTMLElement;
    const clicaFora = target.closest('.usuario');

    if (!clicaFora && this.configON) {
      this.configON = false;
    }
  }

  logout() {
    this.loginService.logout();
  }

  atualizarDashboard(semestre: string) {
    this.exibindoTeste = false;
    this.mostrarDashboard = true;
    this.cdr.detectChanges(); // Força a atualização do dashboard
    // this.inicializarGrafico();
    // Lógica para atualizar a dashboard com os dados do semestre selecionado
    console.log(`Atualizando a dashboard para o semestre: ${semestre}`);

    // Aqui você pode chamar um serviço ou atualizar uma variável para refletir os dados do semestre selecionado
    // Exemplo: this.dadosDashboard = this.servico.obterDados(semestre);
  }

  atualizarDashboardDisciplinas(disciplina: string) {
    this.exibindoTeste = false;
    this.mostrarDashboard = true;
    this.cdr.detectChanges(); // Força a atualização do dashboard
    // this.inicializarGrafico();
    console.log(`Atualizando a dashboard para a disciplina: ${disciplina}`);
  }

  mostrarTeste() {
    this.mostrarDashboard = false;
    this.exibindoTeste = true;
    // this.destruirGrafico();
  }

  // Função para destruir o gráfico quando não for necessário
  // private destruirGrafico() {
  //   if (this.chartInstance) {
  //     this.chartInstance.destroy();
  //     this.chartInstance = null;
  //   }
  // }

  // Inicializar o menu suspenso
  private inicializarMenuSuspenso() {
    const toggleItems = document.querySelectorAll<HTMLButtonElement>('.toggle');
    toggleItems.forEach(item => {
      item.addEventListener('click', (event: MouseEvent) => {
        const submenu = item.nextElementSibling as HTMLElement;

        // Verifica se o submenu existe antes de manipular
        if (submenu) {
          const isExpanded = submenu.style.display === 'block';
          submenu.style.display = isExpanded ? 'none' : 'block';
          item.setAttribute('aria-expanded', !isExpanded ? 'true' : 'false');
        }
      });
    });
  }
}
