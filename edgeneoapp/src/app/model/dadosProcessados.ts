export type DadosProcessados = {
    id: number;
    curso_id: number;
    dados_brutos_id: number;
    duracao_curso: number;
    idade_inicio_curso: number;
    periodo_porcentagem: number;
    percentual_frequencia_periodo: number;
    historico_recente_cr: number;
    historico_recente_percentual_frequencia: number;
    historico_recente_media: number;
    historico_recentes_aprovadas_quantidade: number;
    historico_recentes_aprovadas_media: number;
    historico_recentes_reprovadas_quantidade: number;
    historico_recentes_reprovadas_media: number;
    historico_recente_cursadas: number;
    historico_recente_classe_rendimento: string;
    historico_recente_classe_desempenho: string;
    historico_recente_classe_disciplinas_cursadas: string;
    historico_geral_cr: number;
    historico_geral_percentual_frequencia: number;
    historico_geral_media: number;
    historico_geral_aprovadas_quantidade: number;   
    historico_geral_aprovadas_media: number;
    historico_geral_reprovadas_quantidade: number;
    historico_geral_reprovadas_media: number;
    historico_geral_cursadas: number;
}