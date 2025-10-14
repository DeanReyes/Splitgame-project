import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Juego, RegistrarJuegoRequest, JuegoResponse } from '../models/juego.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class JuegoService {
  private apiUrl = `${environment.apiUrl}/juegos`;

  constructor(private http: HttpClient) {}

  /**
   * Registra un nuevo juego con ganador aleatorio
   */
  registrar(request: RegistrarJuegoRequest): Observable<JuegoResponse> {
    return this.http.post<JuegoResponse>(this.apiUrl, request);
  }

  /**
   * Obtiene los últimos juegos
   */
  obtenerUltimos(limite: number = 10): Observable<JuegoResponse[]> {
    const params = new HttpParams().set('limite', limite.toString());
    return this.http.get<JuegoResponse[]>(`${this.apiUrl}/ultimos`, { params });
  }
}