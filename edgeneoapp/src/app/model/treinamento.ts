export type Treinamento = {
    id: number;
    curso_id_fk: number;
    administrador_id_fk: number;
    dados_brutos_id_fk: number;
    dados_processados_id_fk: number;
    algoritmo_utilizado: string;
    data_hora: Date;
    acuracia: number;
    previsao: string;
    tempo_execucao: number;
}