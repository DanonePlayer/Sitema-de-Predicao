import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { TelaAcepCoordComponent } from './tela-acep-coord/tela-acep-coord.component';
import { loginService } from '../../service/login/loginService';
import { TelaDadosComponent } from './tela-dados/tela-dados.component';
import { AdministradorServiceService } from '../../service/administrador/administrador-service.service';

@Component({
  selector: 'app-tela-home-adm',
  standalone: true,
  imports: [FormsModule, CommonModule, TelaAcepCoordComponent, TelaDadosComponent],
  templateUrl: './tela-home-adm.component.html',
  styleUrl: './tela-home-adm.component.css'
})
export class TelaHomeAdmComponent {
  configON = false;
  mostrarCoordenadores = false;
  mostrarDados = false;
  mostrarTreinamento = false;
  periodoSelecionado: string = "Período 7";
  dadosTreinamento: any = null;

  constructor(
    private loginService: loginService,
    private administradorService: AdministradorServiceService) {}

  configs()
  {
    this.configON = !this.configON;
  }

  realizarTreinamento(): void {
    this.administradorService.treinar().subscribe(
      (response: any) => {
        console.log(response);
        this.dadosTreinamento = response;
      },
      error => {
        console.error('Erro ao treinar:', error);
      }
    );
  }


  selecionarPeriodo(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.periodoSelecionado = selectElement.value; // Atualiza o período selecionado com o valor do select
  }

  @HostListener('document:click', ['$event'])
  fechaconfigs(event: Event)
  {
    const target = event.target as HTMLElement;
    const clicaFora = target.closest('.usuario');

    if (!clicaFora && this.configON) {
      this.configON = false;
    }
  }

  mostrarTelaCoordenadores()
  {
    this.mostrarCoordenadores = true;
    this.mostrarDados = false;
    this.mostrarTreinamento = false;
  }

  mostrarTelaDados()
  {
    this.mostrarCoordenadores = false;
    this.mostrarDados = true;
    this.mostrarTreinamento = false;
  }

  mostrarTelaTreinamento() {
    this.mostrarCoordenadores = false;
    this.mostrarDados = false;
    this.mostrarTreinamento = true;
  }

  logout() {
    // Usando a instância do loginService injetado
    this.loginService.logout();
  }
}
