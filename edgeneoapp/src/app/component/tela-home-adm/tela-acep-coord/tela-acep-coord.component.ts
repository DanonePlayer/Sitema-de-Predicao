import { Component, OnInit } from '@angular/core';
import { Coordenador } from '../../../model/coordenador';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../../../model/usuario';
import { Curso } from '../../../model/curso';
import { UsuarioService } from '../../../service/usuario/usuario.service';
import { CursoService } from '../../../service/curso/curso.service';
import { CoordenadorService } from '../../../service/coordenador/coordenador.service';
import { OrderByPortariaAprovadaPipe } from '../../../pipes/order-by-portaria-aprovada.pipe';

@Component({
  selector: 'app-tela-acep-coord',
  standalone: true,
  imports: [FormsModule, CommonModule, OrderByPortariaAprovadaPipe],
  templateUrl: './tela-acep-coord.component.html',
  styleUrls: ['./tela-acep-coord.component.css']
})
export class TelaAcepCoordComponent implements OnInit {
  coordenadores: Coordenador[] = [];
  coordenadoresEmEdicao: Set<number> = new Set<number>(); // IDs dos coordenadores em edição
  usuariosMap: { [id: number]: Usuario } = {};  // Cache para armazenar dados de usuários
  cursosMap: { [id: number]: string } = {};  // Cache para armazenar nomes de cursos
  dadosCarregados: boolean = false;  // Flag de controle para garantir que os dados estão prontos


  constructor(
    private coordenadorService: CoordenadorService,
    private usuarioService: UsuarioService,
    private cursoService: CursoService
  ) {}

  ngOnInit(): void {
    this.carregarCoordenadores();
  }

  carregarCoordenadores(): void {
    this.coordenadorService.getCoordenadores().subscribe((coordenadores: Coordenador[]) => {
      const promises = coordenadores.map(coordenador =>
        this.carregarDadosCoordenador(coordenador)
      );

      // Quando todas as promessas forem resolvidas, podemos habilitar a flag "dadosCarregados"
      Promise.all(promises).then(() => {
        this.dadosCarregados = true;
        this.coordenadores = coordenadores; // Agora podemos exibir os coordenadores
      });
    });
  }

  // Função para carregar os dados de um coordenador (usuário e curso)
  carregarDadosCoordenador(coordenador: Coordenador): Promise<void> {
    return new Promise<void>((resolve) => {
      this.usuarioService.getUsuarioById(coordenador.usuario_id).subscribe((usuario: Usuario) => {
        this.usuariosMap[coordenador.usuario_id] = usuario;
        this.cursoService.getCursoById(coordenador.curso_id).subscribe((curso: Curso) => {
          this.cursosMap[coordenador.curso_id] = curso.cursoNome;
          resolve(); // Resolve a promise quando os dados do coordenador forem carregados
        });
      });
    });
  }

  getUsuarioNome(usuarioId: number): string {
    return this.usuariosMap[usuarioId] ? this.usuariosMap[usuarioId].nome : '';
  }

  getNomeCurso(cursoId: number): string {
    return this.cursosMap[cursoId] ? this.cursosMap[cursoId] : 'Curso não encontrado';
  }

  baixarPortaria(coordenadorId: number): void {
    // Primeiro, obtenha os dados do coordenador para acessar o nome do usuário
    this.coordenadorService.getCoordenadorById(coordenadorId).subscribe(coordenador => {
      const usuarioId = coordenador.usuario_id;  // Acesse o nome do usuário (assumindo que Coordenador tem o campo 'usuario' com o nome)

      const nomeUsuario = this.getUsuarioNome(usuarioId);

      // Agora, faça o download da portaria
      this.coordenadorService.baixarPortaria(coordenadorId).subscribe((response: Blob) => {
        const blob = new Blob([response], { type: 'application/pdf' });
        const url = window.URL.createObjectURL(blob);

        const link = document.createElement('a');
        link.href = url;

        // Define o nome do arquivo para o download usando o nome do usuário
        link.download = `portaria_${nomeUsuario.replace(/\s+/g, '_')}.pdf`;  // Substitui espaços por underscores

        // Força o clique para iniciar o download
        link.click();

        // Libera a URL criada para evitar vazamentos de memória
        window.URL.revokeObjectURL(url);
      }, error => {
        console.error('Erro ao baixar a portaria:', error);
      });
    })
  }

  excluirCoordenador(coordenador: Coordenador): void {
    // Exibe uma mensagem de confirmação
    const confirmacao = confirm(`Tem certeza que deseja excluir o coordenador ${this.getUsuarioNome(coordenador.usuario_id)}?`);

    // Se o usuário confirmar, procede com a exclusão
    if (confirmacao) {
      this.coordenadorService.excluirCoordenador(coordenador.id).subscribe(() => {
        // Remove o coordenador da lista após exclusão bem-sucedida
        this.coordenadores = this.coordenadores.filter(c => c.id !== coordenador.id);
        alert('Coordenador excluído com sucesso.');
      }, error => {
        // Trata erros, exibe uma mensagem de erro
        console.error('Erro ao excluir o coordenador:', error);
        alert('Erro ao excluir o coordenador. Tente novamente.');
      });
    }
  }
  aprovarCoordenador(coordenador: Coordenador): void {
    const usuario = this.usuariosMap[coordenador.usuario_id];
    if (usuario) {
      usuario.portariaAprovada = true;  // Atualiza o atributo portariaAprovada no objeto Usuario

      // Atualiza o status do coordenador
      coordenador.status = 'ATIVO';

      // Atualiza primeiro o usuário (usuarioService), depois o coordenador (coordenadorService)
      this.usuarioService.atualizarUsuario(usuario).subscribe(() => {
        this.coordenadorService.atualizarCoordenador(coordenador).subscribe(() => {
          // Sucesso, agora você pode tratar o que acontece após a aprovação
          console.log('Coordenador aprovado com sucesso.');
        });
      });
    }
  }

  naoAprovarCoordenador(coordenador: Coordenador): void {
    const usuario = this.usuariosMap[coordenador.usuario_id];
    if (usuario) {
      usuario.portariaAprovada = false;  // Define como não aprovado no objeto Usuario

      // Atualiza o status do coordenador para INATIVO
      coordenador.status = 'INATIVO';

      // Atualiza primeiro o usuário (usuarioService), depois o coordenador (coordenadorService)
      this.usuarioService.atualizarUsuario(usuario).subscribe(() => {
        this.coordenadorService.atualizarCoordenador(coordenador).subscribe(() => {
          // Sucesso, agora você pode tratar o que acontece após a não aprovação
          console.log('Coordenador não aprovado.');
        });
      });
    }
  }
}
