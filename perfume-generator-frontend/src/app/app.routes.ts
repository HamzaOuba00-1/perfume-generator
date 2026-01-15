import { Routes } from '@angular/router';
import { GeneratePerfumeComponent } from './pages/generate-perfume/generate-perfume.component';
import { ResultPerfumeComponent } from './pages/result-perfume/result-perfume.component';

export const routes: Routes = [

  // ================= ADMIN =================
  {
    path: 'admin',
    loadComponent: () =>
      import('./pages/admin/admin-login/admin-login.component')
        .then(m => m.AdminLoginComponent)
  },
  {
    path: 'admin/dashboard',
    loadComponent: () =>
      import('./pages/admin/admin-dashboard/admin-dashboard.component')
        .then(m => m.AdminDashboardComponent)
  },

  // ================= USER =================
  { path: '', component: GeneratePerfumeComponent },
  { path: 'result', component: ResultPerfumeComponent },

  // ================= FALLBACK =================
  { path: '**', redirectTo: '' }
];
