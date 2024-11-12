export type TesteCoordenador = {
    id: number;
    coordenador_id_fk: number;
    treinamento_id_fk: number;
    desempenho_disciplinas_por_periodo: Text;
    desempenho_turmas: Text;
    data_previsao: Date;
}