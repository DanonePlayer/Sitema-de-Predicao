import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CadastroService } from '../../service/cadastro/cadastro.service';
import { Router } from '@angular/router';
import { ETipo } from '../../model/ETipo';
import { Coordenador } from '../../model/coordenador';
import { CoordenadorService } from '../../service/coordenador/coordenador.service';
import { Usuario } from '../../model/usuario';
import { CursoService } from '../../service/curso/curso.service';
import { Curso } from '../../model/curso';

@Component({
  selector: 'app-tela-cadastro',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-cadastro.component.html',
  styleUrls: ['./tela-cadastro.component.css']
})
export class TelaCadastroComponent {
  coordenadorA = false;
  cursos: Curso[] = [];
  cursoSelecionado: number = 0;
  usuario: Usuario = {
    nome: '',
    email: '',
    senha: '',
    tipo: ETipo.ALUNO,
    dataCadastro: new Date(),
    status: 'ATIVO',
    cadastroPessoalCompleto: false,
    cadastroHistoricoCompleto: false,
    portariaAprovada: false,
    coordenador: undefined
  };
  coordenador: Coordenador = {
    id: 0,
    status: 'INATIVO',
    portaria: '',
    usuario_id: 0,
    curso_id: 0,
  };

  confirmarSenha: string = '';

  resultadoPredicao: string = '';
  erroPredicao: string = '';

  erro: string = '';
  file: File | null = null;
  nomeArquivo: string | null = null;


  constructor(private cadastroService: CadastroService,
    private coordenadorService: CoordenadorService,
    private cursoService: CursoService,
    private router: Router) {}

  ngOnInit(): void{
    this.carregarCursos();
  }



  validateEmail(email: string): boolean {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
  }

  // Método para buscar cursos do backend
  carregarCursos(): void {
    this.cursoService.listarCursos().subscribe(
      (response: Curso[]) => {
        this.cursos = response;  // Armazena a lista de cursos no array `cursos`
      },
      error => {
        console.error('Erro ao carregar cursos:', error);
        this.erro = 'Erro ao carregar cursos. Tente novamente mais tarde.';
      }
    );
  }

  // Método para capturar o arquivo selecionado
  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files?.length) {
      this.file = input.files[0]; // Armazena o arquivo na variável `file`
      this.nomeArquivo = this.file.name; // Atualiza o nome do arquivo para exibir
    } else {
      this.file = null;
      this.nomeArquivo = null;
    }
  }

  onCursoChange(event: any): void {
    this.cursoSelecionado = parseInt(event.target.value, 10);
  }

  cadastrar(form: NgForm) {
    if (form.valid) {
      if (!this.validateEmail(this.usuario.email)) {
        this.erro = 'O email é inválido.';
        return;
      }
      if (this.usuario.senha !== this.confirmarSenha) {
        this.erro = 'As senhas não coincidem.';
        return;
      }

      const usuarioParaEnvio: any = { ...this.usuario }; // Clona o objeto do usuário

      // Se for coordenador, adiciona os atributos específicos de coordenador ao objeto de envio
      if (this.coordenadorA) {
        if (!this.cursoSelecionado || this.cursoSelecionado === 0) {
          this.erro = 'Selecione um curso válido para o coordenador.';
          return;
        }

        usuarioParaEnvio.tipo = ETipo.COORDENADOR;
        usuarioParaEnvio.coordenador = {
          status: 'INATIVO',
          portaria: this.coordenador.portaria,
          curso_id: this.cursoSelecionado
        };
      }

      // Realiza o cadastro do usuário e recebe a resposta do backend
      this.cadastroService.cadastrar(usuarioParaEnvio).subscribe(
        (response: any) => {
          // Verifica se o coordenador foi criado e o ID retornado
          if (this.coordenadorA && response.coordenadorId) {
            // Realiza o upload do arquivo usando o coordenadorId retornado
            this.uploadPortaria(response.coordenadorId);
            alert("Cadastro realizado com sucesso! Por favor, verifique seu e-mail para confirmar a conta.");
            this.router.navigate(['/login']);
          } else {
            // Caso contrário, redireciona imediatamente para a tela de login
            alert("Cadastro realizado com sucesso! Por favor, verifique seu e-mail para confirmar a conta.");
            this.router.navigate(['/login']);
          }
        },
        // error => {
        //   this.erro = error.error || 'Erro ao tentar cadastrar o usuário.';
        // }
      );
    } else {
      this.erro = 'Preencha todos os campos corretamente.';
    }
  }


  coordenadorAnexo(event: Event)
  {
    const check = event.target as HTMLInputElement;
    this.coordenadorA = check.checked;
  }

 // Função para realizar o upload do arquivo de portaria
  uploadPortaria(coordenadorId: number) {
  if (this.file) {
    const formData = new FormData();
    formData.append('file', this.file, this.file.name); // Adiciona o arquivo ao FormData

    // Chama o serviço de upload passando o coordenadorId correto
    this.coordenadorService.uploadPortaria(coordenadorId, formData).subscribe(
      uploadResponse => {
        // Redireciona para a tela de login após o upload bem-sucedido
        this.router.navigate(['/login']);
      // },
      // uploadError => {
      //   this.erro = 'Erro ao tentar fazer upload do documento. Tente novamente.';
      //   // console.error('Erro ao fazer upload do documento', uploadError);
      }
    );
  }

}
}
