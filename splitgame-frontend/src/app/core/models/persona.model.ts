export interface Persona {
  id: number;
  nombre: string;
  fechaCreacion: string;
}

export interface CrearPersonaRequest {
  nombre: string;
}

export interface PersonaResponse {
  id: number;
  nombre: string;
  fechaCreacion: string;
}