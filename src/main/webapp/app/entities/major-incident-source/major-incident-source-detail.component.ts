import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMajorIncidentSource } from 'app/shared/model/major-incident-source.model';

@Component({
  selector: 'jhi-major-incident-source-detail',
  templateUrl: './major-incident-source-detail.component.html'
})
export class MajorIncidentSourceDetailComponent implements OnInit {
  majorIncidentSource: IMajorIncidentSource | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ majorIncidentSource }) => (this.majorIncidentSource = majorIncidentSource));
  }

  previousState(): void {
    window.history.back();
  }
}
