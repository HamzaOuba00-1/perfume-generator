import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EssentialOil } from '../../models/essential-oil.model';
import { PerfumeRequest } from '../../models/perfume-request.model';
import { PerfumeResponse } from '../../models/perfume-response.model';

@Injectable({
  providedIn: 'root'
})
export class PerfumeService {

  private readonly API_BASE = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getAllOils(): Observable<EssentialOil[]> {
    return this.http.get<EssentialOil[]>(
      `${this.API_BASE}/oils`
    );
  }

  generatePerfume(request: PerfumeRequest): Observable<PerfumeResponse> {
    return this.http.post<PerfumeResponse>(
      `${this.API_BASE}/perfumes/generate`,
      request
    );
  }
}
