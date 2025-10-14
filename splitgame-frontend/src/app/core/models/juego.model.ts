export interface Juego {
  id: number;
  ganadorId: number;
  fechaJuego: string;
  totalParticipantes: number;
  participantesIds: number[];
}

export interface RegistrarJuegoRequest {
  participantesIds: number[];
}

export interface JuegoResponse {
  id: number;
  ganadorId: number;
  fechaJuego: string;
  totalParticipantes: number;
  participantesIds: number[];
}