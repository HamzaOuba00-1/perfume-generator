import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NgClass, NgStyle } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { EssentialOil } from '../../../../models/essential-oil.model';

@Component({
  selector: 'app-oil-card',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './oil-card.component.html',
  styleUrl: './oil-card.component.css',
})
export class OilCardComponent {
  @Input() oil!: EssentialOil;
  @Input() selected = false;
  @Output() toggle = new EventEmitter<void>();

  onToggle(): void {
    this.toggle.emit();
  }

  onKeyDown(e: KeyboardEvent) {
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      this.onToggle();
    }
  }

  get noteLabel(): string {
    return {
      TOP: 'Top Note',
      HEART: 'Heart Note',
      BASE: 'Base Note',
    }[this.oil.noteType];
  }

  get powerLabel(): string {
    return ['strong', 'medium', 'light'][this.oil.power - 1] || 'unknown';
  }

  get accentColor(): string {
    const key = this.oil.name.toLowerCase();
    return this.generateElegantRandomColor(key);
  }

  private safeFilename(name: string): string | null {
    const cleaned = name.trim();
    if (!/^[a-zA-Z0-9._-]+$/.test(cleaned)) return null;
    return cleaned;
  }

  get imageSrc(): string {
    const uploaded = this.oil.imageUrl
      ? this.safeFilename(this.oil.imageUrl)
      : null;
    if (uploaded) {
      return `http://localhost:8080/uploads/oils/${encodeURIComponent(uploaded)}`;
    }
    return `assets/oils/${this.oil.id}.png`;
  }

  private generateElegantRandomColor(seed: string): string {
    let hash = 0;
    for (let i = 0; i < seed.length; i++) {
      hash = seed.charCodeAt(i) + ((hash << 5) - hash);
    }
    const hue = ((hash % 360) + 360) % 360;
    return `hsl(${hue}, 45%, 70%)`;
  }
}
