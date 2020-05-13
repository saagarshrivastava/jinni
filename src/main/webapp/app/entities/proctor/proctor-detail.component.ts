import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProctor } from 'app/shared/model/proctor.model';

@Component({
  selector: 'jhi-proctor-detail',
  templateUrl: './proctor-detail.component.html'
})
export class ProctorDetailComponent implements OnInit {
  proctor: IProctor | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proctor }) => (this.proctor = proctor));
  }

  previousState(): void {
    window.history.back();
  }
}
