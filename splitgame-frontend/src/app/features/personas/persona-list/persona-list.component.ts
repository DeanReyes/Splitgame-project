import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PersonaResponse } from '../../../core/models/persona.model';
import { PersonaService } from '../../../core/services/persona.service';

@Component({
  selector: 'app-persona-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './persona-list.component.html',
  styleUrl: './persona-list.component.css'
})
export class PersonaListComponent implements OnInit {
  personas: PersonaResponse[] = [];
  loading = false;
  error: string | null = null;

  constructor(private personaService: PersonaService) {}

  ngOnInit(): void {
    this.cargarPersonas();
  }

  cargarPersonas(): void {
    this.loading = true;
    this.error = null;
    
    this.personaService.obtenerTodas().subscribe({
      next: (data) => {
        this.personas = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error al cargar personas';
        this.loading = false;
        console.error('Error:', err);
      }
    });
  }

  eliminarPersona(id: number): void {
    if (confirm('¿Estás seguro de eliminar esta persona?')) {
      this.personaService.eliminar(id).subscribe({
        next: () => {
          this.cargarPersonas();
        },
        error: (err) => {
          this.error = 'Error al eliminar persona';
          console.error('Error:', err);
        }
      });
    }
  }
}
