import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdminService } from '../../../core/services/admin.service';
import { PerfumeService } from '../../../core/services/perfume.service';
import { EssentialOil } from '../../../models/essential-oil.model';

@Component({
  selector: 'app-admin-oil-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-oil-list.component.html',
  styleUrl: './admin-oil-list.component.css',
})
export class AdminOilListComponent implements OnInit {

  oils: EssentialOil[] = [];
  loading = false;
  error?: string;

  constructor(
    private adminService: AdminService,
    private perfumeService: PerfumeService
  ) {}

  ngOnInit(): void {
    this.loadOils();
  }

  loadOils(): void {
    this.loading = true;
    this.error = undefined;

    this.perfumeService.getAllOils().subscribe({
      next: oils => {
        this.oils = oils;
        this.loading = false;
      },
      error: () => {
        this.error = 'Impossible de charger les huiles.';
        this.loading = false;
      }
    });
  }

  deleteOil(oil: EssentialOil): void {
    if (!confirm(`Supprimer l’huile "${oil.name}" ?`)) {
      return;
    }

    this.adminService.deleteOil(oil.id!).subscribe({
      next: () => {
        this.oils = this.oils.filter(o => o.id !== oil.id);
      },
      error: err => {
        this.error = err.error?.message ?? 'Erreur lors de la suppression.';
      }
    });
  }
}
