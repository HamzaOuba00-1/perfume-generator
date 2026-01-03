import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { PerfumeService } from '../../core/services/perfume.service';
import { EssentialOil } from '../../models/essential-oil.model';
import { OilCardComponent } from '../../components/oil-card/oil-card.component';

import { RulesModalComponent } from '../../components/rules-modal/rules-modal.component';
import { PerfumeStateService } from '../../core/services/perfume-state.service';


@Component({
  selector: 'app-generate-perfume',
  standalone: true,
  imports: [CommonModule, OilCardComponent, RulesModalComponent],
  templateUrl: './generate-perfume.component.html',
  styleUrl: './generate-perfume.component.css',

})
export class GeneratePerfumeComponent implements OnInit {

  oils: EssentialOil[] = [];
  selectedOils: EssentialOil[] = [];

  loadingOils = false;
  generating = false;
  error?: string;

  // ✅ FIX
  showRules = false;

  constructor(
    private perfumeService: PerfumeService,
    private perfumeState: PerfumeStateService,
    private router: Router
  ) {}


  ngOnInit(): void {
    this.loadOils();
  }

  // ========================
  // RULES POPUP
  // ========================
  openRules(): void {
    this.showRules = true;
  }

  closeRules(): void {
    this.showRules = false;
  }

  // ========================
  // LOAD OILS
  // ========================
  loadOils(): void {
    this.loadingOils = true;
    this.error = undefined;

    this.perfumeService.getAllOils().subscribe({
      next: oils => {
        this.oils = oils;
        this.loadingOils = false;
      },
      error: () => {
        this.error = 'Impossible de charger les huiles.';
        this.loadingOils = false;
      }
    });
  }

  // ========================
  // SELECTION
  // ========================
  toggleOil(oil: EssentialOil): void {
    this.isSelected(oil)
      ? this.selectedOils = this.selectedOils.filter(o => o.id !== oil.id)
      : this.selectedOils.push(oil);
  }

  isSelected(oil: EssentialOil): boolean {
    return this.selectedOils.some(o => o.id === oil.id);
  }

  // ========================
  // GENERATION
  // ========================
  generatePerfume(): void {

    if (this.selectedOils.length < 3) {
      this.error = 'Veuillez sélectionner au moins 3 huiles.';
      return;
    }

    this.error = undefined;
    this.generating = true;

    this.perfumeService.generatePerfume({
      oils: this.selectedOils.map(o => o.name)
    }).subscribe({
      next: result => {
        this.generating = false;
        this.perfumeState.setResult(result);
        this.router.navigate(['/result']);


      },
      error: err => {
        this.error = err.error?.message ?? 'Erreur lors de la génération.';
        this.generating = false;
      }
    });
  }
}

