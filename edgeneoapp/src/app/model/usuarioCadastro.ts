import { Coordenador } from './coordenador';
import { ETipo } from './ETipo';

export interface UsuarioCadastro {
  nome: string;
  email: string;
  senha: string;
  tipo: ETipo;
  dataCadastro: Date;
  status: string;
  ultimoLogin?: Date;
  curso?: number;
  cadastroPessoalCompleto: boolean;
  cadastroHistoricoCompleto: boolean;
  portariaAprovada: boolean;
  coordenador?: Coordenador; // Adiciona a propriedade opcional coordenador
}
