import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-generation-conditions-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './generation-conditions-modal.component.html',
  styleUrl: './generation-conditions-modal.component.css',
})
export class GenerationConditionsModalComponent {
  @Output() close = new EventEmitter<void>();
}
