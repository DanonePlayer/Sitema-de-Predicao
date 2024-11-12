export type TesteAluno = {
    id: number;
    aluno_id_fk: number;
    treinamento_id_fk: number;
    desempenho_disciplinas: Text;
    probabilidade_aprovacao_disciplinas: Text;
    probabilidade_reprovacao_disciplinas: Text;
    data_previsao: Date;
}