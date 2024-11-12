import { AfterViewInit, Component, Inject, Input, PLATFORM_ID, SimpleChanges } from '@angular/core';
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

@Component({
  selector: 'app-tela-dashboard-aluno',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-dashboard-aluno.component.html',
  styleUrls: ['./tela-dashboard-aluno.component.css']
})
export class TelaDashboardAlunoComponent implements AfterViewInit {
  @Input() resultadoPredicao: string = '';
  @Input() disciplina: { nome: string, id: number } = { nome: '', id: 0 };

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngAfterViewInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      Chart.register(LinearScale, CategoryScale, BarElement, BarController, DoughnutController, ArcElement, Tooltip, Legend);
      if (this.disciplina && this.disciplina.id) {
        this.exibirGrafico();
      }
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['disciplina'] && this.disciplina && this.disciplina.id) {
      this.exibirGrafico();
    }
  }
  
  exibirGrafico() {
    const progressCanvas = document.getElementById('progressBar') as HTMLCanvasElement;
    if (progressCanvas) {
      const data = this.getDataForDisciplina(this.disciplina.id);
      new Chart(progressCanvas, {
        type: 'bar',
        data: {
          labels: [''],
          datasets: [
            {
              label: 'Aprovado',
              data: [data.aprovado],
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1,
              barThickness: 50
            },
            {
              label: 'Reprovado',
              data: [data.reprovado],
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
              ticks: { stepSize: 100 },
              grid: { display: false },
              border: { display: true, width: 5 }
            },
            y: {
              stacked: true,
              grid: { display: false },
              border: { display: false }
            }
          },
          plugins: { legend: { display: true } }
        }
      });
    }
  }

  getDataForDisciplina(disciplinaId: number) {
    switch (disciplinaId) {
      case 1: return { aprovado: 85, reprovado: 15 };
      case 2: return { aprovado: 70, reprovado: 30 };
      case 3: return { aprovado: 60, reprovado: 40 };
      default: return { aprovado: 50, reprovado: 50 };
    }
  }

}
