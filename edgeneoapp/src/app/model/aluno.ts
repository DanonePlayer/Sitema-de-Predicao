export type Aluno = {
    // id: number;
    usuarioId: number;
    cursoId: number;
    sobrenome: string;
    possuiDeficiencia: boolean;
    deficiencias: string;
    cep: string;
    rua: string;
    bairro: string;
    cidade: string;
    estado: string;
    estadoCivil: string;
    genero: string;
    dtNascimento: Date;
    naturalidade: string;
    etnia: string;
    bolsas: string;
    peridoBolsainicio: Date;
    peridoBolsafim: Date;
}
