import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Aluno } from '../../model/aluno';
import { Router } from '@angular/router';
import { AlunoService } from '../../service/aluno/aluno.service';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/login/auth.service';
import { CepService } from '../../service/cep/cep.service';

interface Bolsa {
  tipoBolsa: string;
  inicio: string;
  fim: string;
}

@Component({
  selector: 'app-tela-cadastro-dados',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-cadastro-dados.component.html',
  styleUrls: ['./tela-cadastro-dados.component.css']
})

export class TelaCadastroDadosComponent {
  aluno: Aluno = {
    usuarioId: 0,
    cursoId: 1,
    sobrenome: '',
    possuiDeficiencia: false,
    deficiencias: '',
    cep: '',
    rua: '',
    bairro: '',
    cidade: '',
    estado: '',
    genero: '',
    estadoCivil: '',
    dtNascimento: new Date(),
    naturalidade: '',
    etnia: '',
    bolsas: '',
    peridoBolsainicio: new Date(),
    peridoBolsafim: new Date(),
  };

  dtNascimentoString: string = '';
  minDataNascimento: string = '';
  peridoBolsainicioString: string = '';
  peridoBolsafimString: string = '';

  minDataBolsa: string = '';
  maxDataBolsa: string = '';

  nomeAluno: string = '';

  erro: string = '';

  constructor(
    private alunoService: AlunoService,
    private router: Router,
    private authService: AuthService,
    private cepService: CepService) {}

  ngOnInit(): void {
    const token = this.authService.getToken();
    const dataAtual = new Date();

    this.minDataBolsa = '2008-01-01';

    const maxDate = new Date(dataAtual);
    maxDate.setFullYear(dataAtual.getFullYear() + 1);//a data maxima pode ser so de um ano pra frente com base na data atual
    this.maxDataBolsa = maxDate.toISOString().split('T')[0];

    const usuarioId = token ? this.authService.getIdFromToken(token) : null;
    this.aluno.usuarioId = usuarioId ? usuarioId : 0;
    // console.log('Token atual no localStorage:', this.authService.getToken());

    // Inicializar as strings de data
    // this.dtNascimentoString = this.aluno.dtNascimento ? this.aluno.dtNascimento.toISOString().substring(0, 10) : '';
    // this.peridoBolsainicioString = this.aluno.peridoBolsainicio ? this.aluno.peridoBolsainicio.toISOString().substring(0, 10) : '';
    // this.peridoBolsafimString = this.aluno.peridoBolsafim ? this.aluno.peridoBolsafim.toISOString().substring(0, 10) : '';
    // const usuarioId = localStorage.getItem('idUsuario');
    this.nomeAluno = token ? this.authService.getNomeFromToken(token) || '' : '';
    this.atualizarEstadoCampoDeficiencia();
    this.calcularDataMinima();
  }

  salvar(form: NgForm) {
    if (form.valid) {
      // if (!this.verificarIdadeMinima(this.dtNascimentoString))
      // {
      //   this.erro = 'A idade mínima para cadastro é de 15 anos.';
      //   return;
      // }
      // Atualizar as datas no objeto aluno antes de salvar
      this.aluno.dtNascimento = new Date(this.dtNascimentoString);
      this.aluno.peridoBolsainicio = new Date(this.peridoBolsainicioString);
      this.aluno.peridoBolsafim = new Date(this.peridoBolsafimString);

      this.alunoService.cadastrarAluno(this.aluno).subscribe(
        response => {
          // console.log('Cadastro de Aluno realizado com sucesso', response);
          this.router.navigate(['/cadastro_materias']);
        },
        error => {
          this.erro = 'Erro ao tentar cadastrar o Aluno. Tente novamente.';
          console.error('Erro ao cadastrar aluno', error);
        }
      );
    } else
    {
      this.erro = 'Preencha todos os campos corretamente.';
      console.error('Formulário inválido!');
    }
  }

  buscarEndereco(cep: string): void {
    // console.log('Buscando endereço para o CEP:', cep);
    cep = cep.replace(/\D/g, '');  // Remove caracteres não numéricos
    if (cep && cep.length === 8) {  // Verificar se o CEP possui 8 dígitos
      this.cepService.buscarEnderecoPorCep(cep).subscribe(
        (dados) => {
          if (dados.erro) {
            console.warn('CEP não encontrado.');
            this.erro = 'CEP não encontrado. Verifique e tente novamente.';
            return;
          }

          // Preencher os campos com os dados do endereço retornados pela API
          this.aluno.rua = dados.logradouro;
          this.aluno.bairro = dados.bairro;
          this.aluno.cidade = dados.localidade;
          this.aluno.estado = dados.uf;
          this.erro = '';  // Limpar mensagem de erro
        },
        (error) => {
          console.error('Erro ao buscar o endereço pelo CEP:', error);
          this.erro = 'Erro ao buscar o endereço. Verifique o CEP e tente novamente.';
        }
      );
    }
  }

  formatarCep(): void {
    let cep = this.aluno.cep;
    cep = cep.replace(/\D/g, '');  // Remove caracteres não numéricos
    if (cep.length > 5) {
      cep = cep.replace(/^(\d{5})(\d)/, '$1-$2');  // Aplica a máscara xxxxx-xxx
    }
    this.aluno.cep = cep;

    if (cep.length === 9) {
      this.buscarEndereco(cep);
    }
  }

  bolsas: Bolsa[] = [{ tipoBolsa: '', inicio: '', fim: ''}];


  addBolsa()
  {
    this.bolsas.push({ tipoBolsa: '', inicio: '', fim: '' });
  }

  delBolsa(index: number)
  {
    this.bolsas.splice(index, 1);
  }

  def(valor: boolean)
  {
    this.aluno.possuiDeficiencia = valor;
  }

  public campoObrigatorio(form: NgForm): boolean
  {
    return form.submitted && (
      form.controls['sobrenome']?.invalid ||
      form.controls['genero']?.invalid ||
      form.controls['dataNascimento']?.invalid ||
      form.controls['naturalidade']?.invalid ||
      form.controls['cep']?.invalid ||
      form.controls['estadoCivil']?.invalid
    );
  }

  calcularDataMinima()
  {
    const dataAtual = new Date();
    dataAtual.setFullYear(dataAtual.getFullYear() - 15); //idade minima de 15 anos
    this.minDataNascimento = dataAtual.toISOString().split('T')[0];
    console.log(this.minDataNascimento);
  }

  atualizarEstadoCampoDeficiencia(): void
  {
    const campoTipoDeficiencia = document.getElementById('tipo') as HTMLElement;
    if (this.aluno.possuiDeficiencia === false) {
      campoTipoDeficiencia.classList.add('desabilitado');
    } else {
      campoTipoDeficiencia.classList.remove('desabilitado');
    }
  }

  validarData(bolsa: Bolsa): void {
    if (new Date(bolsa.fim) < new Date(bolsa.inicio))
    {
      this.erro= 'A data de fim não pode ser anterior à data de início.';
      bolsa.fim = '';
    }
    else
    {
      this.erro='';
    }
  }

  onTipoBolsaChange(bolsa: Bolsa): void
  {
    bolsa.inicio = '';
    bolsa.fim = '';
  }

}
