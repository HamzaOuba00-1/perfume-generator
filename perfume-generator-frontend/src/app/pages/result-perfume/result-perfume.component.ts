import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PerfumeResponse } from '../../models/perfume-response.model';
import { PerfumeStateService } from '../../core/services/perfume-state.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-result-perfume',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './result-perfume.component.html',
  styleUrl: './result-perfume.component.css'
})
export class ResultPerfumeComponent implements OnInit {

  result?: PerfumeResponse;

  constructor(
    private perfumeState: PerfumeStateService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.result = this.perfumeState.getResult();
  }

  goBack(): void {
    this.perfumeState.clear();
    this.router.navigate(['/']);
  }
}
