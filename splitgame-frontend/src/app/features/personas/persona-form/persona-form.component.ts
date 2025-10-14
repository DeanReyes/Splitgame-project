import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PersonaService } from '../../../core/services/persona.service';
import { CrearPersonaRequest } from '../../../core/models/persona.model';

@Component({
  selector: 'app-persona-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './persona-form.component.html',
  styleUrls: ['./persona-form.component.css']
})
export class PersonaFormComponent {
  @Output() personaCreada = new EventEmitter<void>();

  nombre = '';
  loading = false;
  error: string | null = null;

  constructor(private personaService: PersonaService) {}

  onSubmit(): void {
    if (!this.nombre.trim()) {
      this.error = 'El nombre no puede estar vacío';
      return;
    }

    this.loading = true;
    this.error = null;

    const request: CrearPersonaRequest = {
      nombre: this.nombre.trim()
    };

    this.personaService.crear(request).subscribe({
      next: () => {
        this.nombre = '';
        this.loading = false;
        this.personaCreada.emit();
      },
      error: (err) => {
        this.error = err.error?.message || 'Error al crear persona';
        this.loading = false;
        console.error('Error:', err);
      }
    });
  }
}
