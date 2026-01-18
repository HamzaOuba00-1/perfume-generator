import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../../core/services/admin.service';

@Component({
  selector: 'app-admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-login.component.html',
  styleUrl: './admin-login.component.css',
})
export class AdminLoginComponent {

  username = '';
  password = '';
  error?: string;
  loading = false;

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  login(): void {
    this.error = undefined;
    this.loading = true;

    this.adminService.login(this.username, this.password);

    this.adminService.ping().subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/admin/dashboard']);
      },
      error: () => {
        this.loading = false;
        this.error = 'Identifiants invalides';
        this.adminService.logout();
      }
    });
  }

}
