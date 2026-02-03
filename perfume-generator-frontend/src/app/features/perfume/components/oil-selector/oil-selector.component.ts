import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  Output,
  ViewChild,
  AfterViewInit,
  OnDestroy,
} from '@angular/core';
import { CommonModule } from '@angular/common';

import { EssentialOil } from '../../../../models/essential-oil.model';
import { OilCardComponent } from '../oil-card/oil-card.component';

@Component({
  selector: 'app-oil-selector',
  standalone: true,
  imports: [CommonModule, OilCardComponent],
  templateUrl: './oil-selector.component.html',
  styleUrl: './oil-selector.component.css',
})
export class OilSelectorComponent implements AfterViewInit, OnDestroy {
  @Input({ required: true }) oils: EssentialOil[] = [];
  @Input() selectedOils: EssentialOil[] = [];

  @Output() toggleOil = new EventEmitter<EssentialOil>();
  @Output() generate = new EventEmitter<void>();

  @ViewChild('selectorRoot', { static: true })
  selectorRoot!: ElementRef<HTMLElement>;

  showGenerate = false;

  query = '';

  ngAfterViewInit(): void {
    window.addEventListener('scroll', this.onScroll, { passive: true });
    this.onScroll();
  }

  ngOnDestroy(): void {
    window.removeEventListener('scroll', this.onScroll);
  }

  onScroll = (): void => {
    const top = this.selectorRoot.nativeElement.getBoundingClientRect().top;

    this.showGenerate = top <= 500;
  };

  onQueryInput(v: string): void {
    this.query = v;
  }

  isSelected(oil: EssentialOil): boolean {
    return this.selectedOils.some(o => o.id === oil.id);
  }

  get filteredOils(): EssentialOil[] {
    const q = this.query.trim().toLowerCase();
    if (!q) return this.oils;
    return this.oils.filter(o => o.name.toLowerCase().includes(q));
  }

  trackById(_: number, oil: EssentialOil): number {
    return oil.id;
  }
}
