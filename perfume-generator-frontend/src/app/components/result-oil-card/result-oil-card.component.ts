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
  @Input({ required: true }) ml!: string; // ex: "3" ou "3.5"

  /** filename stocké en DB (ex: lavande.png) */
  @Input() imageUrl?: string;

  /** 🔥 EXACTEMENT la même logique que OilCardComponent */
  get imageSrc(): string {

    // 1️⃣ Image uploadée via backend
    if (this.imageUrl && this.imageUrl.trim().length > 0) {
      return `http://localhost:8080/uploads/oils/${this.imageUrl}`;
    }

    // 2️⃣ Fallback asset local basé sur le nom
    const slug = this.name
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/[^a-z0-9]+/g, '-')
      .replace(/(^-|-$)/g, '');

    return `/assets/oils/${slug}.png`;
  }
}
