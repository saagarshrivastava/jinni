import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMajorIncident } from 'app/shared/model/major-incident.model';

@Component({
  selector: 'jhi-major-incident-detail',
  templateUrl: './major-incident-detail.component.html'
})
export class MajorIncidentDetailComponent implements OnInit {
  majorIncident: IMajorIncident | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ majorIncident }) => (this.majorIncident = majorIncident));
  }

  previousState(): void {
    window.history.back();
  }
}
