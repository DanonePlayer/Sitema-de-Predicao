export type Disciplina =
{
    id: number;
    cursoId: number;
    alunoId: number;
    disciplinaCodigo: string;
    disciplinaCreditos: number;
    disciplinaNome: string;
    disciplinaPeriodo: number;
    nota?: number;
    totalFaltas?: number;
    situacao?: string;

    obrigatorio?: boolean;
    preenchida?: boolean; 
};
