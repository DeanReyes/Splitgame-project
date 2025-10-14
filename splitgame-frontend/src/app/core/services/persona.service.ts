import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Persona, CrearPersonaRequest, PersonaResponse } from '../models/persona.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PersonaService {
  private apiUrl = `${environment.apiUrl}/personas`;

  constructor(private http: HttpClient) {}

  /**
   * Obtiene todas las personas
   */
  obtenerTodas(): Observable<PersonaResponse[]> {
    return this.http.get<PersonaResponse[]>(this.apiUrl);
  }

  /**
   * Obtiene una persona por ID
   */
  obtenerPorId(id: number): Observable<PersonaResponse> {
    return this.http.get<PersonaResponse>(`${this.apiUrl}/${id}`);
  }

  /**
   * Crea una nueva persona
   */
  crear(request: CrearPersonaRequest): Observable<PersonaResponse> {
    return this.http.post<PersonaResponse>(this.apiUrl, request);
  }

  /**
   * Elimina una persona
   */
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}