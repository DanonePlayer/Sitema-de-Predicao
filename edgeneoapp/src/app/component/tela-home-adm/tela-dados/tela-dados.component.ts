import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NtiApiService } from '../../../service/nti-api.service';
import { DadosbrutosService } from '../../../service/dadosbruto/dadosbrutos.service';
import { DadosBrutos } from '../../../model/dadosBrutos';

@Component({
  selector: 'app-tela-dados',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-dados.component.html',
  styleUrl: './tela-dados.component.css'
})
export class TelaDadosComponent {
  novoCurso = { codCurso: '', anoIngresso: null, nomeCurso: '', status: '', isCursoValido: false };

  registros: DadosBrutos[] = [];


  constructor(private ntiApiService: NtiApiService, private dadosBrutosService: DadosbrutosService) { }

  ngOnInit(): void {
    this.carregarRegistros();
  }

  // Carregar os registros já existentes no banco
  carregarRegistros(): void {
    this.dadosBrutosService.get().subscribe(
      (registros) => {
        this.registros = registros;
      },
      (error) => {
        console.error('Erro ao carregar registros:', error);
      }
    );
  }

  buscarCurso(): void {
    const curso = this.novoCurso;
    if (curso.codCurso && curso.anoIngresso) {
      this.ntiApiService.buscarNomeCurso(curso.codCurso, curso.anoIngresso).subscribe(
        (nomeCurso: string) => {
          if (nomeCurso !== 'Curso não encontrado') {
            curso.nomeCurso = nomeCurso;
            curso.isCursoValido = true; // Ativa o botão de start
            curso.status = 'Pronto'; // Atualiza o status para "Em andamento"
          } else {
            curso.nomeCurso = 'Curso inválido';
            curso.isCursoValido = false; // Mantém o botão de start desabilitado
            curso.status = 'Inválido'; // Atualiza o status para "Inválido"
          }
        },
        (error) => {
          curso.nomeCurso = 'Erro ao buscar curso';
          curso.isCursoValido = false; // Mantém o botão de start desabilitado
          curso.status = 'Inválido'; // Atualiza o status para "Inválido"
        }
      );
    }
  }

  // Método para iniciar a requisição de dados brutos após validação do curso
  iniciarRequisicao(): void {
    const curso = this.novoCurso;
    if (curso.codCurso && curso.anoIngresso) {
      curso.status = 'Em andamento'; // Atualiza o status para "Em andamento"
      this.ntiApiService.obterDadosAlunos(curso.anoIngresso, curso.codCurso).subscribe(
        (response: string) => {
          console.log('Requisição concluída:', response);
          curso.status = 'Concluído'; // Atualiza o status para "Concluído"
          this.carregarRegistros(); // Atualiza a lista de registros salvos
          this.resetNovoCurso(); // Reseta o formulário após a requisição
        },
        (error) => {
          console.error('Erro ao iniciar a requisição:', error);
          curso.status = 'Erro'; // Atualiza o status para "Erro"
        }
      );
    }
  }

  // Função para resetar o curso temporário após a requisição
  resetNovoCurso(): void {
    this.novoCurso = { codCurso: '', anoIngresso: null, nomeCurso: '', status: '', isCursoValido: false };
  }

  podeRemover(index: number): boolean {
    return index > 0; // Habilita remover apenas para registros da base de dados
  }

  // Função para remover registro
  removerRegistro(id: number): void {
    this.dadosBrutosService.delete(id).subscribe(
      () => {
        console.log('Registro removido com sucesso.');
        this.carregarRegistros(); // Recarregar a tabela após remoção
      },
      (error) => {
        console.error('Erro ao remover registro:', error);
      }
    );
  }

}
