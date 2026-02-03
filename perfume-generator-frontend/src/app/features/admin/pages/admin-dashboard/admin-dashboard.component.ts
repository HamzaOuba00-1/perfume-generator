import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AdminService } from '../../../../core/services/admin.service';
import { AdminOilFormComponent } from '../../../admin/components/admin-oil-form/admin-oil-form.component';
import { AdminOilListComponent } from '../../../admin/components/admin-oil-list/admin-oil-list.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    AdminOilFormComponent,
    AdminOilListComponent,
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})
export class AdminDashboardComponent {

  constructor(
    private adminService: AdminService,
    private router: Router
  ) {}

  logout(): void {
    this.adminService.logout();
    this.router.navigate(['/admin/login'], { replaceUrl: true });
  }

}
