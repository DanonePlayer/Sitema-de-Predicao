import { AfterViewInit, Component, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  Chart,
  LinearScale,
  CategoryScale,
  BarElement,
  DoughnutController,
  BarController,
  Tooltip,
  Legend,
  ArcElement
} from 'chart.js';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-tela-dashboard-coord',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-dashboard-coord.component.html',
  styleUrls: ['./tela-dashboard-coord.component.css']
})
export class TelaDashboardCoordComponent implements AfterViewInit {
  dadosTeste: any = null;

  constructor(
    // private testeService: TesteService,
    private route: ActivatedRoute,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  ngOnInit(): void {
    // Inicializa com valores padrão caso 'dadosTeste' ainda não tenha sido carregado
    this.dadosTeste = {
      totalInstancias: 0,
      instanciasCorretas: 0,
      acuracia: 0,
      instanciasIncorretas: 0,
      erro: 0
    };
  }  

  ngAfterViewInit(): void {
    // Verifica se está sendo executado no navegador
    if (isPlatformBrowser(this.platformId)) {
      // Recupera os dados do teste da URL
      this.route.queryParams.subscribe(params => {
        this.dadosTeste = JSON.parse(params['dados']); // Dados enviados via queryParams
        this.atualizarCards();
      });

      // Registrar os componentes do Chart.js, incluindo ArcElement
      Chart.register(LinearScale, CategoryScale, BarElement, BarController, DoughnutController, ArcElement, Tooltip, Legend);

      // Gráfico de Barras
      const progressCanvas = document.getElementById('progressBar') as HTMLCanvasElement;
      if (progressCanvas) {
        new Chart(progressCanvas, {
          type: 'bar',
          data: {
            labels: [''],
            datasets: [
              {
                label: 'Correto',
                data: [90],
                backgroundColor: 'rgba(54, 162, 235, 0.2)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1,
                barThickness: 50
              },
              {
                label: 'Incorreto',
                data: [10],
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1,
                barThickness: 50
              }
            ]
          },
          options: {
            indexAxis: 'y',
            scales: {
              x: {
                stacked: true,
                beginAtZero: true,
                max: 100,
                ticks: {
                  stepSize: 100,
                },
                grid: {
                  display: false,
                },
                border: {
                  display: true,
                  width: 5,
                }
              },
              y: {
                stacked: true,
                grid: {
                  display: false,
                },
                border: {
                  display: false
                }
              }
            },
            layout: {
              padding: {
                left: 0,
                right: 0,
                top: 100,
                bottom: 100
              }
            },
            plugins: {
              legend: {
                display: true
              }
            }
          }
        });
      }
    }
  }

  

  atualizarCards(): void {
    if (this.dadosTeste) {
      // Atribui valores a variáveis de forma segura
      const totalInstancias = this.dadosTeste.totalInstancias ? this.dadosTeste.totalInstancias.toString() : '0';
      const instanciasCorretas = this.dadosTeste.instanciasCorretas && this.dadosTeste.acuracia
        ? `${this.dadosTeste.instanciasCorretas} - ${this.dadosTeste.acuracia}%`
        : '0 - 0%';
      const instanciasIncorretas = this.dadosTeste.instanciasIncorretas && this.dadosTeste.erro
        ? `${this.dadosTeste.instanciasIncorretas} - ${this.dadosTeste.erro}%`
        : '0 - 0%';
  
      // Atualiza os cards com os valores
      this.atualizarCard(0, totalInstancias, 'Número Total de Instâncias');
      this.atualizarCard(1, instanciasCorretas, 'Instâncias Classificadas Corretamente');
      this.atualizarCard(2, instanciasIncorretas, 'Instâncias Classificadas Incorretamente');
    }
  }
  
  atualizarCard(cardIndex: number, numberText: string, cardName: string): void {
    const cardNumber = document.querySelectorAll('.card')[cardIndex]?.querySelector('.number');
    const cardNameElement = document.querySelectorAll('.card')[cardIndex]?.querySelector('.card-name');
  
    if (cardNumber) {
      cardNumber.textContent = numberText;
    }
    if (cardNameElement) {
      cardNameElement.textContent = cardName;
    }
  }
  
  
}
