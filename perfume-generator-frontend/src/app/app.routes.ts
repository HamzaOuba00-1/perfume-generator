import { Routes } from '@angular/router';
import { GeneratePerfumeComponent } from './features/perfume/pages/generate-perfume/generate-perfume.component';
import { ResultPerfumeComponent } from './features/perfume/pages/result-perfume/result-perfume.component';

export const routes: Routes = [

  // ================= ADMIN =================
  {
    path: 'admin',
    children: [

      {
        path: 'login',
        loadComponent: () =>
          import('./features/admin/pages/admin-login/admin-login.component')
            .then(m => m.AdminLoginComponent)
      },

      {
        path: 'dashboard',
        loadComponent: () =>
          import('./features/admin/pages/admin-dashboard/admin-dashboard.component')
            .then(m => m.AdminDashboardComponent)
      },

      // Default admin route
      { path: '', redirectTo: 'login', pathMatch: 'full' }
    ]
  },

  // ================= USER =================
  { path: '', component: GeneratePerfumeComponent },
  { path: 'result', component: ResultPerfumeComponent },

  // ================= FALLBACK =================
  { path: '**', redirectTo: '' }
];
