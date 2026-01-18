import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EssentialOil } from '../../models/essential-oil.model';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private readonly apiUrl = 'http://localhost:8080/api/admin';

  constructor(private http: HttpClient) {}

  // =========================
  // AUTH
  // =========================

  /**
   * Stocke les credentials admin (Basic Auth)
   */
  login(username: string, password: string): void {
    const token = btoa(`${username}:${password}`);
    sessionStorage.setItem('admin_auth', token);
  }

  logout(): void {
    sessionStorage.removeItem('admin_auth');
  }

  isLoggedIn(): boolean {
    return !!sessionStorage.getItem('admin_auth');
  }

  // =========================
  // HEADERS
  // =========================

  private getAuthHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('admin_auth');
    return new HttpHeaders({
      Authorization: `Basic ${token}`
    });
  }

  // =========================
  // CRUD HUILES
  // =========================

  createOil(oil: Partial<EssentialOil>): Observable<EssentialOil> {
    return this.http.post<EssentialOil>(
      `${this.apiUrl}/oils`,
      oil,
      { headers: this.getAuthHeaders() }
    );
  }

  updateOil(id: number, oil: Partial<EssentialOil>): Observable<EssentialOil> {
    return this.http.put<EssentialOil>(
      `${this.apiUrl}/oils/${id}`,
      oil,
      { headers: this.getAuthHeaders() }
    );
  }

  deleteOil(id: number): Observable<void> {
    return this.http.delete<void>(
      `${this.apiUrl}/oils/${id}`,
      { headers: this.getAuthHeaders() }
    );
  }

  // =========================
  // UPLOAD IMAGE
  // =========================

  uploadOilImage(file: File): Observable<{ imageUrl: string }> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<{ imageUrl: string }>(
      `${this.apiUrl}/oils/upload`,
      formData,
      { headers: this.getAuthHeaders() }
    );
  }



  ping(): Observable<void> {
    return this.http.get<void>(
      `${this.apiUrl}/ping`,
      { headers: this.getAuthHeaders() }
    );
  }

}
