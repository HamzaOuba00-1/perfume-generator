import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { PerfumeStateService } from '../../../../core/services/perfume-state.service';
import { ResultOilCardComponent } from '../../../perfume/components/result-oil-card/result-oil-card.component';
import { PerfumeService } from '../../../../core/services/perfume.service';

type ResultOil = {
  name: string;
  percent: number;
  imageUrl?: string;
};

type PerfumeResult = {
  oils: ResultOil[];
};

@Component({
  selector: 'app-result-perfume',
  standalone: true,
  imports: [CommonModule, ResultOilCardComponent],
  templateUrl: './result-perfume.component.html',
  styleUrl: './result-perfume.component.css',
})
export class ResultPerfumeComponent implements OnInit {
  result?: PerfumeResult;

  volumes: number[] = [5, 10, 15, 20];
  selectedVolume = 10;

  constructor(
    private perfumeService: PerfumeService,
    private perfumeState: PerfumeStateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const raw: any = this.perfumeState.getResult();

    if (!raw) {
      this.router.navigate(['/generate']);
      return;
    }

    const composition: any[] = Array.isArray(raw.composition)
      ? raw.composition
      : [];

    this.result = {
      oils: composition
        .map((x: any) => ({
          name: String(x?.oilName ?? '').trim(),
          percent: Number(x?.percentage ?? 0),
          imageUrl: x?.imageUrl ? String(x.imageUrl) : undefined,
        }))
        .filter(
          (o: ResultOil) =>
            o.name.length > 0 && Number.isFinite(o.percent) && o.percent > 0
        ),
    };

    if (this.result.oils.length === 0) {
      this.router.navigate(['/generate']);
    }
  }

  setVolume(v: number): void {
    this.selectedVolume = v;
  }

  oilMl(oil: ResultOil): number {
    return (this.selectedVolume * oil.percent) / 100;
  }

  formatMl(v: number): string {
    const s = v.toFixed(1);
    return s.endsWith('.0') ? s.slice(0, -2) : s;
  }

  oilImage(oilName: string): string {
    const slug = oilName
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[^a-z0-9]+/g, '-')
      .replace(/(^-|-$)/g, '');

    return `/assets/oils/${slug}.png`;
  }

  trackByName(_: number, o: ResultOil): string {
    return o.name;
  }

  downloadPdf(): void {
    if (!this.result) return;

    const payload = {
      volume: this.selectedVolume,
      oils: this.result.oils.map((o) => ({
        name: o.name,
        percent: o.percent,
      })),
    };

    this.perfumeService.downloadPdf(payload).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);

        const a = document.createElement('a');
        a.href = url;
        a.download = 'fragrance-formula.pdf';
        a.click();

        window.URL.revokeObjectURL(url);
      },
      error: (err) => {
        console.error('PDF error', err);
      },
    });
  }
}
