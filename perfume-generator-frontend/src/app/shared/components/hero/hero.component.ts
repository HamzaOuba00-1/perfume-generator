import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ViewportScroller } from '@angular/common';

@Component({
  selector: 'app-hero',
  standalone: true,
  templateUrl: './hero.component.html',
  styleUrls: ['./hero.component.css'],
})
export class HeroComponent {

  constructor(
    private scroller: ViewportScroller,
    private router: Router
  ) {}

  scrollTo(anchor: string): void {
  const go = () => {
    const el = document.getElementById(anchor);
    if (!el) return;

    const navH = parseInt(getComputedStyle(document.documentElement)
      .getPropertyValue('--nav-h')) || 80;

    const y = el.getBoundingClientRect().top + window.scrollY - navH - 16;
    window.scrollTo({ top: y, behavior: 'smooth' });
  };

  if (this.router.url !== '/generate') {
    this.router.navigate(['/generate']).then(() => setTimeout(go, 50));
  } else {
    go();
  }
}


}
