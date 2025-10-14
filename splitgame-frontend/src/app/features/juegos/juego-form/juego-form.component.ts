import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PersonaService } from '../../../core/services/persona.service';
import { JuegoService } from '../../../core/services/juego.service';
import { PersonaResponse } from '../../../core/models/persona.model';
import { RegistrarJuegoRequest, JuegoResponse } from '../../../core/models/juego.model';

@Component({
  selector: 'app-juego-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './juego-form.component.html',
  styleUrls: ['./juego-form.component.css']
})
export class JuegoFormComponent implements OnInit {
  @Output() juegoRegistrado = new EventEmitter<JuegoResponse>();

  personas: PersonaResponse[] = [];
  personasSeleccionadas: number[] = [];
  loading = false;
  error: string | null = null;
  resultado: JuegoResponse | null = null;

  constructor(
    private personaService: PersonaService,
    private juegoService: JuegoService
  ) {}

  ngOnInit(): void {
    this.cargarPersonas();
  }

  cargarPersonas(): void {
    this.personaService.obtenerTodas().subscribe({
      next: (data) => {
        this.personas = data;
      },
      error: (err) => {
        this.error = 'Error al cargar personas';
        console.error('Error:', err);
      }
    });
  }

  togglePersona(id: number): void {
    const index = this.personasSeleccionadas.indexOf(id);
    if (index > -1) {
      this.personasSeleccionadas.splice(index, 1);
    } else {
      this.personasSeleccionadas.push(id);
    }
  }

  isSeleccionada(id: number): boolean {
    return this.personasSeleccionadas.includes(id);
  }

  jugar(): void {
    if (this.personasSeleccionadas.length < 2) {
      this.error = 'Selecciona al menos 2 personas';
      return;
    }

    this.loading = true;
    this.error = null;
    this.resultado = null;

    const request: RegistrarJuegoRequest = {
      participantesIds: this.personasSeleccionadas
    };

    this.juegoService.registrar(request).subscribe({
      next: (data) => {
        this.resultado = data;
        this.loading = false;
        this.juegoRegistrado.emit(data);
      },
      error: (err) => {
        this.error = err.error?.message || 'Error al jugar';
        this.loading = false;
        console.error('Error:', err);
      }
    });
  }

  resetear(): void {
    this.personasSeleccionadas = [];
    this.resultado = null;
    this.error = null;
  }

  getNombreGanador(): string {
    if (!this.resultado) return '';
    const ganador = this.personas.find(p => p.id === this.resultado!.ganadorId);
    return ganador?.nombre || 'Desconocido';
  }
}
