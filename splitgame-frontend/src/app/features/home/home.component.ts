import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PersonaListComponent } from '../personas/persona-list/persona-list.component';
import { PersonaFormComponent } from '../personas/persona-form/persona-form.component';
import { JuegoFormComponent } from '../juegos/juego-form/juego-form.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    PersonaListComponent,
    PersonaFormComponent,
    JuegoFormComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  @ViewChild(PersonaListComponent) personaList!: PersonaListComponent;
  @ViewChild(JuegoFormComponent) juegoForm!: JuegoFormComponent;

  onPersonaCreada(): void {
    // Recargar lista de personas
    if (this.personaList) {
      this.personaList.cargarPersonas();
    }
    // Recargar personas en el formulario de juego
    if (this.juegoForm) {
      this.juegoForm.cargarPersonas();
    }
  }

  onJuegoRegistrado(): void {
    // Aquí podrías mostrar un historial si lo implementas
    console.log('Juego registrado exitosamente');
  }
}
