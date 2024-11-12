import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-tela-todas-disciplinas',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './tela-todas-disciplinas.component.html',
  styleUrls: ['./tela-todas-disciplinas.component.css']
})
export class TelaTodasDisciplinasComponent {
  @Input() disciplinas: { nome: string, situacao: string, id: number }[] = [];

}
