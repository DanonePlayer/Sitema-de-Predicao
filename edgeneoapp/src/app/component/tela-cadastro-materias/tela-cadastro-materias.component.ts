import { Component, ElementRef, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { DisciplinaService } from '../../service/disciplina/disciplina.service';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { PLATFORM_ID, Inject } from '@angular/core';
import { AlunoDisciplinas } from '../../model/alunoDisciplinas';
import { AuthService } from '../../service/login/auth.service';
import { Disciplina } from '../../model/disciplina';
import { FormsModule } from '@angular/forms';
import { AlunoService } from '../../service/aluno/aluno.service';
import { loginService } from '../../service/login/loginService';

@Component({
  selector: 'app-tela-cadastro-materias',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-cadastro-materias.component.html',
  styleUrl: './tela-cadastro-materias.component.css'
})
export class TelaCadastroMateriasComponent
{
  alunoDisciplinas: AlunoDisciplinas = {
    usuarioId: 0,
    periodoAtual: '',
    anoIngresso: '',
    formaIngresso: '',
    disciplinas: [],
    totalFaltas: [],
    situacao: [],
    notas: []
  };

  todasDisciplinas: Disciplina[] = [];
  disciplinasEscolhidas: Disciplina[] = [];
  disciplinaSelecionada: Disciplina | null = null;
  periodos: number[] = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  periodosDisponiveis: number[] = [];
  erro: string = '';

  mensagemErro: boolean = false;
  nomeDisciplina: string = '';
  periodoDisciplina: number | null = null;
  salvarHabilitado: boolean = false;

  constructor(
    private disciplinaService: DisciplinaService,
    private router: Router,
    private loginService: loginService,
    private authService: AuthService,
    private alunoService: AlunoService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) {}

  @HostListener('document:click', ['$event'])
  onClick(event: Event): void
  {
    const clicoDentro = (event.target as HTMLElement).closest('.caixa-diciplina');
    if (!clicoDentro)
    {
      this.disciplinaSelecionada = null;
    }
  }

  ngOnInit(): void
  {
    if(isPlatformBrowser(this.platformId))
    {
      this.addDragScrollFunctionality();
    }
    const token = this.authService.getToken();
    const usuarioId = token ? this.authService.getIdFromToken(token) : null;
    this.alunoDisciplinas.usuarioId =  usuarioId ? usuarioId : 0;

    // Carregar todas as disciplinas do backend
    this.carregarDisciplinas();
    this.atualizarPeriodos();
  }

  // Método para carregar todas as disciplinas do backend
  carregarDisciplinas(): void 
  {
    this.disciplinaService.getAllDisciplinas().subscribe(
      (disciplinas: Disciplina[]) => {
        this.todasDisciplinas = disciplinas.map(disciplina => ({...disciplina,preenchida: false  }));
        console.log('Disciplinas carregadas com sucesso', this.todasDisciplinas);
      },
      (error: HttpErrorResponse) => {
        this.erro = 'Erro ao carregar disciplinas';
        console.error(error);
      }
    );
  }

  addDragScrollFunctionality(): void {
    const periodos = document.querySelector('.periodos') as HTMLElement;

    if (periodos) {
      let isDown = false;
      let startX: number;
      let scrollLeft: number;

      periodos.addEventListener('mousedown', (e: MouseEvent) => {
        isDown = true;
        startX = e.pageX - periodos.offsetLeft;
        scrollLeft = periodos.scrollLeft;
        periodos.style.cursor = 'grabbing';
      });

      periodos.addEventListener('mouseleave', () => {
        isDown = false;
        periodos.style.cursor = 'grab';
      });

      periodos.addEventListener('mouseup', () => {
        isDown = false;
        periodos.style.cursor = 'grab';
      });

      periodos.addEventListener('mousemove', (e: MouseEvent) => {
        if (!isDown) return;
        e.preventDefault();
        const x = e.pageX - periodos.offsetLeft;
        const walk = (x - startX) * 1; // Ajuste de velocidade
        periodos.scrollLeft = scrollLeft - walk;
      });
    }
  }

  atualizarPeriodos(): void
  {
    const periodoAtual = parseInt(this.alunoDisciplinas.periodoAtual, 10);
    const certoperiodo = periodoAtual -1;
    
    if (!isNaN(certoperiodo) && certoperiodo > 0) {
      this.periodosDisponiveis = this.periodos.filter(p => p < certoperiodo);
      this.marcarDisciplinasObrigatorias();
    } else 
    {
      this.periodosDisponiveis = [];
    }
  }

  marcarDisciplinasObrigatorias(): void 
  {
    const periodoAtual = parseInt(this.alunoDisciplinas.periodoAtual, 10);
    this.todasDisciplinas.forEach(disciplina => {
      disciplina.obrigatorio = disciplina.disciplinaPeriodo < periodoAtual;

      if (!disciplina.obrigatorio) 
      {
        // Resetar disciplina que não é mais obrigatória e removê-la de disciplinasEscolhidas
        disciplina.preenchida = false;
        disciplina.nota = 0;
        disciplina.totalFaltas = 0;
        disciplina.situacao = undefined;

        // Remover da lista de disciplinasEscolhidas
        this.disciplinasEscolhidas = this.disciplinasEscolhidas.filter(d => d.id !== disciplina.id);
      }
    });
    this.verificarDisciplinasObrigatoriasPreenchidas();
  }

  verificarDisciplinasObrigatoriasPreenchidas(): void {
    this.salvarHabilitado = this.todasDisciplinas
      .filter(disciplina => disciplina.obrigatorio)
      .every(disciplina => disciplina.preenchida);
  }

  salvarDisciplinas(): void 
  {
    const todasObrigatoriasPreenchidas = this.todasDisciplinas
     .filter(disciplina => disciplina.obrigatorio)
     .every(disciplina => disciplina.preenchida);

    // console.log(todasObrigatoriasPreenchidas);
    // console.log(this.todasDisciplinas);
    // console.log(this.disciplinasEscolhidas);
    this.todasDisciplinas.forEach(disciplina => {
      console.log(`Disciplina: ${disciplina.disciplinaNome}, Obrigatório: ${disciplina.obrigatorio}, Preenchida: ${disciplina.preenchida}`);
    });
    // console.log(this.todasDisciplinas);
    if (!todasObrigatoriasPreenchidas) 
    {
        // Caso não estejam todas preenchidas, impedir o salvamento e exibir uma mensagem
        this.erro = 'Preencha todas as disciplinas obrigatórias antes de salvar.';
        console.log("errado");
        return;
    }

    this.alunoDisciplinas.notas = [];
    this.alunoDisciplinas.totalFaltas = [];
    this.alunoDisciplinas.situacao = [];

    this.alunoDisciplinas.disciplinas = this.disciplinasEscolhidas
    .filter(disciplina => disciplina.situacao !== 'naoFez')
    .map(disciplina => {
      this.alunoDisciplinas.notas.push(disciplina.nota || 0);
      this.alunoDisciplinas.totalFaltas.push(disciplina.totalFaltas || 0);
      this.alunoDisciplinas.situacao.push(disciplina.situacao || '');

      const { nota, totalFaltas, situacao, ...disciplinaBasica } = disciplina;
      return disciplinaBasica;
    });
    
    console.log("Preparando para enviar:", this.alunoDisciplinas);
    this.alunoService.associarDisciplinasAoAluno(this.alunoDisciplinas, this.alunoDisciplinas.usuarioId).subscribe(
      (response) => {
        console.log('Disciplinas associadas com sucesso:', response);
        localStorage.setItem('successMessage', 'true');
        this.router.navigate(['/home_aluno']);
      },
      (error: HttpErrorResponse) => {
        console.error('Erro ao associar disciplinas:', error);
        this.erro = 'Erro ao salvar disciplinas. Tente novamente mais tarde.';
      }
    );
  }

  abrirNota(disciplina: Disciplina): void
  {
    this.disciplinaSelecionada = disciplina;
    console.log(this.disciplinaSelecionada.situacao);
    if (this.disciplinaSelecionada.situacao === 'Trancamento' || this.disciplinaSelecionada.situacao === 'naoFez') 
    {
      setTimeout(() =>this.bloquearCampos(true), 0); 
    } 

    else 
    {
      setTimeout(() => this.bloquearCampos(false), 0);
    }

    this.atualizarEstadoSalvar();
  }

  atualizarNota(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (this.disciplinaSelecionada) {
      this.disciplinaSelecionada.nota = parseFloat(inputElement.value);
      this.atualizarEstadoSalvar();
    }
  }

  atualizarFaltas(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (this.disciplinaSelecionada) {
      this.disciplinaSelecionada.totalFaltas = parseInt(inputElement.value, 10);
      this.atualizarEstadoSalvar();
    }
  }

  atualizarSituacao(event: Event): void {
    const inputElement = event.target as HTMLInputElement;
    if (this.disciplinaSelecionada) 
    {
      const novaSituacao = inputElement.value;
    
      this.disciplinaSelecionada.situacao = novaSituacao;

      this.disciplinaSelecionada.nota = 0;
      this.disciplinaSelecionada.totalFaltas = 0;
  
      // Bloqueia ou desbloqueia os campos de nota e faltas
      if (this.disciplinaSelecionada.situacao === 'Trancamento'|| this.disciplinaSelecionada.situacao === 'naoFez') 
      {
        this.disciplinaSelecionada.nota = 0;
        this.disciplinaSelecionada.totalFaltas = 0;
        this.bloquearCampos(true);
      } 

      else 
      {
        this.bloquearCampos(false);
      }
      this.atualizarEstadoSalvar();
    }
  }  

  salvarNota(): void {
    if (this.disciplinaSelecionada)
    {
      if (this.disciplinaSelecionada.nota! > 10)
      {
        this.disciplinaSelecionada.nota = 10;
      }

      else if (this.disciplinaSelecionada.nota! < 0)
      {
        this.disciplinaSelecionada.nota = 0;
      }

      const disciplinaIndex = this.disciplinasEscolhidas.findIndex(d => d.id === this.disciplinaSelecionada?.id);

      if (disciplinaIndex !== -1) {
        this.disciplinasEscolhidas[disciplinaIndex].nota = this.disciplinaSelecionada.nota;
        this.disciplinasEscolhidas[disciplinaIndex].totalFaltas = this.disciplinaSelecionada.totalFaltas;
        this.disciplinasEscolhidas[disciplinaIndex].situacao = this.disciplinaSelecionada.situacao;
        
        this.disciplinasEscolhidas[disciplinaIndex].obrigatorio = true;
        this.disciplinasEscolhidas[disciplinaIndex].preenchida = true;
      } 
      
      else 
      {
        this.disciplinaSelecionada.obrigatorio = true;
        this.disciplinaSelecionada.preenchida = true;
        this.disciplinasEscolhidas.push(this.disciplinaSelecionada);
      }

      this.disciplinaSelecionada = null;
    }
  }

  fecharMensagemErro(): void
  {
    this.mensagemErro = false;
  }

  logout() 
  {
    this.loginService.logout();
  }

  bloquearCampos(bloquear: boolean): void 
  {
    const notaInput = document.getElementById('nota') as HTMLInputElement;
    const faltasInput = document.getElementById('faltas') as HTMLInputElement;
    if (notaInput && faltasInput) 
    {
      notaInput.disabled = bloquear;
      faltasInput.disabled = bloquear;
    }
  }

  podeSalvar(): boolean 
  {
    if (!this.disciplinaSelecionada) return false;

    const { nota, totalFaltas, situacao } = this.disciplinaSelecionada;
    if (!situacao) return false;

    if (situacao === 'Aprovado') 
    {

      if (nota !== undefined && nota >= 5 && (totalFaltas?? 0) <= 9) 
        {
        return true;
      }
      return false;
    }

    if (situacao === 'Reprovado') {
      if(nota !== undefined && totalFaltas !== undefined && (totalFaltas > 0 || nota > 0) && (nota <= 5 || totalFaltas > 9)) 
      {
        return true;
      }
      return false;
    }

    if (situacao === 'Trancamento') return true;

    return nota !== undefined && nota >= 0 && nota <= 10 && totalFaltas !== undefined;
  }

  atualizarEstadoSalvar(): void 
  {
    this.salvarHabilitado = this.podeSalvar();
  }

}
