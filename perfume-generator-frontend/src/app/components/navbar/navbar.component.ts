import { Component } from '@angular/core';
import { CommonModule, ViewportScroller } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent {
  constructor(
    private scroller: ViewportScroller,
    private router: Router
  ) {}

  scrollTo(anchor: string): void {
    // si on n'est pas déjà sur /generate, on y va d'abord
    if (this.router.url !== '/generate') {
      this.router.navigate(['/generate']).then(() => {
        setTimeout(() => {
          this.scroller.scrollToAnchor(anchor);
        }, 50);
      });
    } else {
      this.scroller.scrollToAnchor(anchor);
    }
  }
}
