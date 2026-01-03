import { Routes } from '@angular/router';
import { GeneratePerfumeComponent } from './pages/generate-perfume/generate-perfume.component';
import { ResultPerfumeComponent } from './pages/result-perfume/result-perfume.component';

export const routes: Routes = [
  { path: '', component: GeneratePerfumeComponent },
  { path: 'result', component: ResultPerfumeComponent },
  { path: '**', redirectTo: '' }
];
