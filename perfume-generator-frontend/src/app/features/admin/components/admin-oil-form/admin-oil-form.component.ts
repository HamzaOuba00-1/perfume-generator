import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AdminService } from '../../../../core/services/admin.service';

@Component({
  selector: 'app-admin-oil-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-oil-form.component.html',
  styleUrl: './admin-oil-form.component.css'
})
export class AdminOilFormComponent {

  name = '';
  noteType: 'TOP' | 'HEART' | 'BASE' = 'TOP';
  power = 2;
  maxPercent = 30;

  selectedFile?: File;
  imageUrl?: string;

  loading = false;
  message?: string;
  error?: string;

  constructor(private adminService: AdminService) {}

  // =========================
  // FILE
  // =========================

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }


  // =========================
  // SUBMIT
  // =========================

  submit(): void {
    this.error = undefined;
    this.message = undefined;

    if (!this.selectedFile) {
      this.error = 'Veuillez sélectionner une image.';
      return;
    }

    this.loading = true;

    // 1️⃣ Upload image
    this.adminService.uploadOilImage(this.selectedFile).subscribe({
      next: res => {
        this.imageUrl = res.imageUrl;

        // 2️⃣ Création huile
        this.adminService.createOil({
          name: this.name,
          noteType: this.noteType,
          power: this.power,
          maxPercent: this.maxPercent,
          imageUrl: this.imageUrl
        }).subscribe({
          next: () => {
            this.loading = false;
            this.message = 'Huile ajoutée avec succès.';
            this.resetForm();
          },
          error: err => {
            this.loading = false;
            this.error = err.error?.message ?? 'Erreur lors de la création.';
          }
        });
      },
      error: () => {
        this.loading = false;
        this.error = 'Erreur lors de l’upload de l’image.';
      }
    });
  }

  private resetForm(): void {
    this.name = '';
    this.noteType = 'TOP';
    this.power = 2;
    this.maxPercent = 30;
    this.selectedFile = undefined;
    this.imageUrl = undefined;
  }
}
