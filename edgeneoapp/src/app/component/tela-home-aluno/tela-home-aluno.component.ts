import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Component, HostListener, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { loginService } from '../../service/login/loginService';
import { TelaDashboardAlunoComponent } from './tela-dashboard-aluno/tela-dashboard-aluno.component';
import { AlunoService } from '../../service/aluno/aluno.service';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TelaTodasDisciplinasComponent } from './tela-todas-disciplinas/tela-todas-disciplinas.component';

@Component({
  selector: 'app-tela-home-aluno',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule,
    TelaDashboardAlunoComponent,
    TelaTodasDisciplinasComponent
  ],
  templateUrl: './tela-home-aluno.component.html',
  styleUrl: './tela-home-aluno.component.css'
})
export class TelaHomeAlunoComponent implements OnInit {
  configON = false;
  mengSucesso: boolean = false;
  todasDisciplinas = false;
  mostrarAviso = true;
  mostrarAcuracia = true;
  disciplinasVisiveis = false;
  anoDisciplinaVisiveis = false;
  disciplinasPrevistas: { nome: string, id: number }[] = [];
  disciplinasSelecionadas: any[] = [];
  disciplinaSelecionada: { nome: string, id: number } | null = null;
  // anoDisciplinaSelecionado: string | null = null;
  // disciplinasFiltradas: { nome: string, id: number }[] = [];
  disciplinas: { nome: string, situacao: string, id: number}[] = [
    { nome: 'Gestão de Informação', situacao: 'Aprovado', id: 1 },
    { nome: 'Segurança e Auditoria de Sistemas', situacao: 'Aprovado', id: 2 },
    { nome: 'Sistemas Multimídia', situacao: 'Reprovado', id: 3 },
    { nome: 'Empreendedorismo', situacao: 'Aprovado', id: 4 },
    { nome: 'Gerência de Redes', situacao: 'Aprovado', id: 5 },
    { nome: 'Relatório de Estágio', situacao: 'Reprovado', id: 6 },
    { nome: 'Trabalho de Conclusão de Curso II', situacao: 'Reprovado', id: 7 }
  ];


  aluno = {
    id: 1,
    usuario: {
      id: 1,
      nome: 'João'
    },
    curso: {
      id: 1,
      nome: 'Engenharia'
    },
    sobrenome: 'Silva',
    possuiDeficiencia: false,
    deficiencias: '',
    cep: '12345678',
    rua: 'Rua Exemplo',
    bairro: 'Centro',
    cidade: 'Cidade Exemplo',
    estado: 'SP',
    estadoCivil: 'Solteiro',
    genero: 'Masculino',
    dtNascimento: '2000-01-01',
    naturalidade: 'Brasileiro',
    etnia: 'Branco',
    bolsas: 'Nenhuma',
    peridoBolsainicio: null,
    peridoBolsafim: null
  };

  resultadoPredicao: string = '';
  erroPredicao: string = '';

  constructor(
    private loginService: loginService,
    private alunoService: AlunoService,
    private router: Router, @Inject(PLATFORM_ID)
    private platformId: Object) {}

    // selecionarDisciplina(event: Event): void {
    //   const selectElement = event.target as HTMLSelectElement;
    //   this.disciplinaSelecionada = selectElement.value; // Atualiza a disciplina selecionada com o valor do select
    // }
    // selecionarDisciplina(disciplina: any): void {
    //   // Verifique se a disciplina já foi adicionada à lista de disciplinas selecionadas
    //   if (!this.disciplinasSelecionadas.includes(disciplina)) {
    //     this.disciplinasSelecionadas.push(disciplina);
    //   }
    // }

    fecharAviso(): void {
      this.mostrarAviso = false;
    }

  //   selecionarAnoDisciplina(anoDisciplina: string): void {
  //     this.anoDisciplinaSelecionado = anoDisciplina;
  //     this.filtrarDisciplinasPorAnoDisciplina();
  // }

  selecionarDisciplina(disciplina: { nome: string, id: number }) {
    this.disciplinaSelecionada = disciplina;
    this.resultadoPredicao = 'Algum resultado de predição aqui';
  }

    exibirDashboard(disciplina: { nome: string, id: number }): void {
      this.disciplinaSelecionada = disciplina;
    }

    // filtrarDisciplinasPorPeriodo(): void {
    //   console.log("Periodo Selecionado:", this.periodoSelecionado);
    //   this.disciplinasFiltradas = this.disciplinas.filter(disciplina => {
    //     console.log("Comparando disciplina.periodo:", disciplina.periodo);
    //     return disciplina.periodo === this.periodoSelecionado;
    //   });
    // }

    // filtrarDisciplinasPorAnoDisciplina(): void {
    //   if (this.anoDisciplinaSelecionado) {
    //     this.disciplinasFiltradas = this.disciplinas.filter(disciplina => disciplina.periodo === this.anoDisciplinaSelecionado);
    //   } else {
    //     this.disciplinasFiltradas = [];
    //   }
    // }


  ngOnInit(): void {
    if (isPlatformBrowser(this.platformId)) {
      const successMessage = localStorage.getItem('successMessage');

      if (successMessage === 'true') {
        this.mengSucesso = true;
        localStorage.removeItem('successMessage');

        setTimeout(() => {
          const successDiv = document.querySelector('.mensagem-sucesso');
          if (successDiv) {
            successDiv.classList.add('hidden');

            setTimeout
            (() => {
              this.mengSucesso = false;
            }, 3000);
          }
        });
      }
    }
    // console.log("Disciplinas iniciais:", this.disciplinas);
    // console.log("Ano Disciplina inicial:", this.anoDisciplina);


    // this.getdados();
    this.preverSituacao();
    // this.buscarDisciplinas();
  }

  // buscarDisciplinas(): void {
  //   this.alunoService.buscarDisciplinasPorAluno(this.alunoId).subscribe(
  //     (disciplinas: any[]) => {
  //       console.log("Disciplinas recebidas:", disciplinas);  // Verifique os dados retornados

  //       this.disciplinas = disciplinas;  // Armazena as disciplinas
  //       this.filtrarDisciplinasPorAnoDisciplina();  // Filtra disciplinas após o carregamento

  //       // Preenche a lista de anos
  //       // this.anoDisciplina = this.extrairAnosDisciplina(disciplinas);
  //     },
  //     (error) => {
  //       this.erroDisciplinas = 'Erro ao carregar as disciplinas: ' + error.message;
  //     }
  //   );
  // }

  // extrairAnosDisciplina(disciplinas: any[]): { nome: string, id: number }[] {
  //   const anos: Set<string> = new Set();  // Usamos Set para garantir anos únicos

  //   // Percorre as disciplinas e extrai o 'periodo'
  //   disciplinas.forEach(disciplina => {
  //     if (disciplina.periodo) {
  //       anos.add(disciplina.periodo);  // Adiciona o 'periodo' no Set
  //     }
  //   });

  //   // Retorna a lista de anos com um id único para cada ano
  //   return Array.from(anos).map((ano, index) => ({
  //     nome: ano,
  //     id: index + 1  // Atribuindo um id único
  //   }));
  // }

  // mostrarAnoDisciplina(): void {
  //   if (this.anoDisciplinaVisiveis) {
  //     this.anoDisciplinaVisiveis = false;
  //   } else {
  //     this.anoDisciplinaVisiveis = true
  //   }
  // }

  mostrarDisciplinas(): void {
    if (this.disciplinasVisiveis) {
      this.disciplinasVisiveis = false;
    } else {
      this.disciplinasVisiveis = true;
      this.todasDisciplinas = false;
    }
  }

  mostrarTodasDisciplinas(): void {
    this.todasDisciplinas = true;
    this.mostrarAviso = false;
    this.disciplinasVisiveis = false;
    this.disciplinaSelecionada = null;
  }


  getdados(): void {
    this.alunoService.dados().subscribe(
      (response: any) => {
        console.log(response);
      },
      error => {
        console.error('Erro ao dados:', error);
      }
    );
  }

  preverSituacao() {
    this.alunoService.prever(this.aluno).subscribe(
      (resultado: any) => {
        console.log('Resultado da predição:', resultado);

        // // Certifique-se de que o resultado tenha o formato correto: [{ nome: string, id: number }]
        // this.disciplinasPrevistas = resultado.disciplinas.map((disciplina: any, index: number) => ({
        //   nome: disciplina.nome,  // ou qualquer outro campo que você precise
        //   id: index + 1           // Gere um ID único para cada disciplina, se necessário
        // }));

        // Mostrar mensagem de sucesso ou realizar outras ações
        this.mostrarAcuracia = true;
        this.resultadoPredicao = 'Predições realizadas com sucesso!';
      },
      (erro) => {
        this.erroPredicao = 'Erro ao realizar predição: ' + erro.message;
        this.resultadoPredicao = '';
        this.mostrarAcuracia = false;
      }
    );
  }


  configs()
  {
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

  mostrarTelaResultado(): void {
    this.mostrarAcuracia = true;
  }


  iniciarTreinamento() {
    // Coloque aqui o código para iniciar o treinamento
    console.log("Treinamento iniciado");
  }

}
