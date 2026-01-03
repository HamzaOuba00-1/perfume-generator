import { Component, Input, Output, EventEmitter } from '@angular/core';
import { EssentialOil } from '../../models/essential-oil.model';

@Component({
  selector: 'app-oil-card',
  standalone: true,
  templateUrl: './oil-card.component.html',
  styleUrl: './oil-card.component.css'
})
export class OilCardComponent {

  @Input() oil!: EssentialOil;
  @Input() selected = false;
  @Output() toggle = new EventEmitter<void>();

  private readonly colorMap: Record<string, string> = {
    citron: '#F4D35E',
    bergamote: '#F6C177',
    orange: '#F08A5D',
    lavande: '#B8A1D9',
    rose: '#E8A2B5',
    jasmin: '#F5E6C8',
    ylang: '#F2E86D',
    patchouli: '#6B705C',
    vetiver: '#4A5D4E',
    santal: '#CBB994',
    vanille: '#EAD7B7',
    encens: '#9A8C98',
    musc: '#DAD7CD',
  };

  onToggle(): void {
    this.toggle.emit();
  }

  get noteLabel(): string {
    return {
      TETE: 'Note de tête',
      COEUR: 'Note de cœur',
      FOND: 'Note de fond'
    }[this.oil.noteType];
  }

  get powerLabel(): string {
    return ['Forte', 'Moyenne', 'Faible'][this.oil.power - 1];
  }

  /** 🎨 couleur principale de la carte */
  get accentColor(): string {
    const key = this.oil.name.toLowerCase();
    return this.colorMap[key] ?? this.generateElegantRandomColor(key);
  }

  /** 🎲 fallback random mais stable (pas flashy) */
  private generateElegantRandomColor(seed: string): string {
    let hash = 0;
    for (let i = 0; i < seed.length; i++) {
      hash = seed.charCodeAt(i) + ((hash << 5) - hash);
    }

    const hue = hash % 360;
    return `hsl(${hue}, 45%, 70%)`; // luxe pastel
  }
}
