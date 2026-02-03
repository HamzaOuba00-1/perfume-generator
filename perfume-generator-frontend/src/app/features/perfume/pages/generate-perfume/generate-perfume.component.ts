import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { PerfumeService } from '../../../../core/services/perfume.service';
import { EssentialOil } from '../../../../models/essential-oil.model';
import { PerfumeStateService } from '../../../../core/services/perfume-state.service';
import { HeroComponent } from '../../../../shared/components/hero/hero.component';
import { OilSelectorComponent } from '../../../perfume/components/oil-selector/oil-selector.component';
import { ExplainSystemComponent } from '../../../../shared/components/explain-system/explain-system.component';
import { GenerationConditionsModalComponent } from '../../../perfume/components/generation-conditions-modal/generation-conditions-modal.component';

@Component({
  selector: 'app-generate-perfume',
  standalone: true,
  imports: [
    CommonModule,
    HeroComponent,
    OilSelectorComponent,
    ExplainSystemComponent,
    GenerationConditionsModalComponent,
  ],
  templateUrl: './generate-perfume.component.html',
  styleUrl: './generate-perfume.component.css',
})
export class GeneratePerfumeComponent implements OnInit {
  oils: EssentialOil[] = [];
  selectedOils: EssentialOil[] = [];

  loadingOils = false;
  generating = false;
  error?: string;

  showRules = false;
  showConditionsModal = false;

  constructor(
    private perfumeService: PerfumeService,
    private perfumeState: PerfumeStateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadOils();
  }

  openRules(): void {
    this.showRules = true;
  }

  closeRules(): void {
    this.showRules = false;
  }

  loadOils(): void {
    this.loadingOils = true;
    this.error = undefined;

    this.perfumeService.getAllOils().subscribe({
      next: (oils) => {
        this.oils = oils;
        this.loadingOils = false;
      },
      error: () => {
        this.error = 'Impossible de charger les huiles.';
        this.loadingOils = false;
      },
    });
  }

  toggleOil(oil: EssentialOil): void {
    this.isSelected(oil)
      ? (this.selectedOils = this.selectedOils.filter((o) => o.id !== oil.id))
      : this.selectedOils.push(oil);
  }

  isSelected(oil: EssentialOil): boolean {
    return this.selectedOils.some((o) => o.id === oil.id);
  }

  generatePerfume(): void {
    // Reset
    this.error = undefined;

    const count = this.selectedOils.length;

    if (count < 3 || count > 10) {
      this.showConditionsModal = true;
      return;
    }

    const hasTop = this.selectedOils.some((o) => o.noteType === 'TOP');
    const hasHeart = this.selectedOils.some((o) => o.noteType === 'HEART');
    const hasBase = this.selectedOils.some((o) => o.noteType === 'BASE');

    if (!hasTop || !hasHeart || !hasBase) {
      this.showConditionsModal = true;
      return;
    }

    this.generating = true;

    this.perfumeService
      .generatePerfume({
        oils: this.selectedOils.map((o) => o.name),
      })
      .subscribe({
        next: (result) => {
          this.generating = false;
          this.perfumeState.setResult(result);
          this.router.navigate(['/result']);
        },
        error: () => {
          this.generating = false;
          this.showConditionsModal = true;
        },
      });
  }
}
