import { Injectable } from '@angular/core';
import { PerfumeResponse } from '../../models/perfume-response.model';

@Injectable({
  providedIn: 'root'
})
export class PerfumeStateService {

  private result?: PerfumeResponse;

  setResult(result: PerfumeResponse): void {
    this.result = result;
  }

  getResult(): PerfumeResponse | undefined {
    return this.result;
  }

  clear(): void {
    this.result = undefined;
  }
}
