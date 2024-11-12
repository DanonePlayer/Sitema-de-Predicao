import { Pipe, PipeTransform } from '@angular/core';
import { Coordenador } from '../model/coordenador';
import { Usuario } from '../model/usuario';

@Pipe({
  name: 'orderByPortariaAprovada',
  standalone: true
})
export class OrderByPortariaAprovadaPipe implements PipeTransform {
    transform(coordenadores: Coordenador[], usuariosMap: { [id: number]: Usuario }): Coordenador[] {
      // Verifica a existência de usuários associados e ordena de acordo com a portariaAprovada
      return coordenadores.sort((a, b) => {
        const portariaAprovadaA = usuariosMap[a.usuario_id]?.portariaAprovada ?? false;
        const portariaAprovadaB = usuariosMap[b.usuario_id]?.portariaAprovada ?? false;
  
        // Ordenar os coordenadores para que os não aprovados venham primeiro
        return (portariaAprovadaA === portariaAprovadaB) ? 0 : portariaAprovadaA ? 1 : -1;
      });
    }
  }
