import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-rules-modal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './rules-modal.html',
  styleUrls: ['./rules-modal.css']
})
export class RulesModalComponent {

  @Output() close = new EventEmitter<void>();

  onClose(): void {
    this.close.emit();
  }

}
