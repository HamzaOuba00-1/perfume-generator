import { Injectable } from '@angular/core';
import { PerfumeResponse } from '../../models/perfume-response.model';

const STORAGE_KEY = 'perfume_result_v1';

@Injectable({
  providedIn: 'root',
})
export class PerfumeStateService {
  private result?: PerfumeResponse;

  constructor() {
    // ✅ restore depuis sessionStorage (si refresh)
    this.result = this.loadFromStorage();
  }

  setResult(result: PerfumeResponse): void {
    this.result = result;
    this.saveToStorage(result);
  }

  getResult(): PerfumeResponse | undefined {
    // ✅ si state vide mais storage existe, on restaure
    if (!this.result) {
      this.result = this.loadFromStorage();
    }
    return this.result;
  }

  clear(): void {
    this.result = undefined;
    sessionStorage.removeItem(STORAGE_KEY);
  }

  private saveToStorage(result: PerfumeResponse): void {
    try {
      sessionStorage.setItem(STORAGE_KEY, JSON.stringify(result));
    } catch {
      // ignore (storage full / blocked)
    }
  }

  private loadFromStorage(): PerfumeResponse | undefined {
    try {
      const raw = sessionStorage.getItem(STORAGE_KEY);
      if (!raw) return undefined;
      return JSON.parse(raw) as PerfumeResponse;
    } catch {
      return undefined;
    }
  }
}
