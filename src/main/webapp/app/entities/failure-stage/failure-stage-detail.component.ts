import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFailureStage } from 'app/shared/model/failure-stage.model';

@Component({
  selector: 'jhi-failure-stage-detail',
  templateUrl: './failure-stage-detail.component.html'
})
export class FailureStageDetailComponent implements OnInit {
  failureStage: IFailureStage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ failureStage }) => (this.failureStage = failureStage));
  }

  previousState(): void {
    window.history.back();
  }
}
