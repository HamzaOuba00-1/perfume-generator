import { Component, Input, Output, EventEmitter } from '@angular/core';
import { NgClass, NgStyle } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { EssentialOil } from '../../models/essential-oil.model';

@Component({
  selector: 'app-oil-card',
  standalone: true,
  imports: [MatIconModule],
  templateUrl: './oil-card.component.html',
  styleUrl: './oil-card.component.css'
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
      TOP: 'Note de tête',
      HEART: 'Note de cœur',
      BASE: 'Note de fond'
    }[this.oil.noteType];
  }

  get powerLabel(): string {
    return ['Forte', 'Moyenne', 'Faible'][this.oil.power - 1];
  }

  get accentColor(): string {
    const key = this.oil.name.toLowerCase();
    return this.generateElegantRandomColor(key);
  }

  get imageSrc(): string {
    // 1️⃣ Image uploadée depuis l’admin (backend)
    if (this.oil.imageUrl) {
      return `http://localhost:8080/uploads/oils/${this.oil.imageUrl}`;
    }

    // 2️⃣ Fallback asset local
    return `assets/oils/${this.oil.name.toLowerCase()}.png`;
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
