import { Routes } from '@angular/router';
import { TelaLoginComponent } from './component/tela-login/tela-login.component';
import { TelaCadastroComponent } from './component/tela-cadastro/tela-cadastro.component';
import { TelaEsquesenhaComponent } from './component/tela-esquesenha/tela-esquesenha.component';
import { TelaCadastroDadosComponent } from './component/tela-cadastro-dados/tela-cadastro-dados.component';
import { TelaHomeAdmComponent } from './component/tela-home-adm/tela-home-adm.component';
import { TelaHomeCoordenadorComponent } from './component/tela-home-coordenador/tela-home-coordenador.component';
import { TelaHomeAlunoComponent } from './component/tela-home-aluno/tela-home-aluno.component';
import { TelaCadastroMateriasComponent } from './component/tela-cadastro-materias/tela-cadastro-materias.component';
import { TelaCoordenadorEsperaComponent } from './component/tela-coordenador-espera/tela-coordenador-espera.component';
import { authGuard } from './guards/auth.guard';
import { cadastroPessoalGuard } from './guards/cadastroPessoal.guard';
import { cadastroHistoricoGuard } from './guards/cadastroHistorico.guard';
import { NoAuthGuard } from './guards/noAuth.guard';
import path from 'path';
import { ConfirmacaoEmailComponent } from './component/confirmacao-email/confirmacao-email.component';
// import { TelaDashboardComponent } from './component/tela-dashboard/tela-dashboard.component';

export const routes: Routes =
[
  {path: '', canActivate: [NoAuthGuard], children: [ // canActivate: [NoAuthGuard],
    {path: 'login', component: TelaLoginComponent},
    {path: 'cadastro', component: TelaCadastroComponent},
    {path: 'esqueceu_senha', component: TelaEsquesenhaComponent},
  ]},

    { path: 'login/oauth2/code/google', redirectTo: '/cadastro', pathMatch: 'full' },

    {path: 'cadastro_aluno', component: TelaCadastroDadosComponent, canActivate: [cadastroPessoalGuard]},
    {path: 'cadastro_materias', component: TelaCadastroMateriasComponent, canActivate: [cadastroHistoricoGuard]},

    // {path: 'escolher_materias', component: TelaEscolherMateriasComponent, canActivate: [authGuard]},


    {path: 'home_administrador', component: TelaHomeAdmComponent}, //canActivate: [authGuard]},
    {path: 'home_coordenador', component: TelaHomeCoordenadorComponent}, //canActivate: [authGuard]},
    {path: 'home_aluno', component: TelaHomeAlunoComponent},// canActivate: [authGuard]},
    {path: 'coordenador_espera', component: TelaCoordenadorEsperaComponent},// canActivate: [authGuard]},
    // {path: 'cadastro_materias', component: TelaContrucaoComponent, canActivate: [authGuard]},
    { path: 'confirmacao-email', component: ConfirmacaoEmailComponent },

    {path: '', redirectTo: '/login', pathMatch: 'full'}, // vai pra tela de login por padrão, isso é bom quando a tela principal ja estiver pronta
    { path: '**', redirectTo: '' } // url invalida vai pra tela inicial
];
