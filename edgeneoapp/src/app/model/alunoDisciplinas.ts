import { Disciplina } from "./disciplina";

export type AlunoDisciplinas = {
  usuarioId: number;
  periodoAtual: string;
  anoIngresso: string;
  formaIngresso: string;
  disciplinas: Disciplina[];
  totalFaltas: number[];
  situacao: string[];
  notas: number[];
}
