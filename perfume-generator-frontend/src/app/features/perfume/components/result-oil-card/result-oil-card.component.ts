import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-result-oil-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './result-oil-card.component.html',
  styleUrl: './result-oil-card.component.css',
})
export class ResultOilCardComponent {
  @Input({ required: true }) name!: string;
  @Input({ required: true }) ml!: string;

  @Input() imageUrl?: string;

  private readonly FALLBACK_SRC = 'assets/oils/citron.png';

  private safeFilename(name: string): string | null {
    const cleaned = name.trim();
    if (!/^[a-zA-Z0-9._-]+$/.test(cleaned)) return null;
    return cleaned;
  }

  get imageSrc(): string {
    const uploaded = this.imageUrl ? this.safeFilename(this.imageUrl) : null;
    if (uploaded) {
      return `http://localhost:8080/uploads/oils/${encodeURIComponent(uploaded)}`;
    }

    const slug = (this.name || '')
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[^a-z0-9]+/g, '-')
      .replace(/(^-|-$)/g, '');

    if (!slug) return this.FALLBACK_SRC;

    return `assets/oils/${slug}.png`;
  }

  onImgError(e: Event): void {
    const img = e.target as HTMLImageElement;

    if (img.src.endsWith(this.FALLBACK_SRC)) return;

    img.src = this.FALLBACK_SRC;
  }
}
