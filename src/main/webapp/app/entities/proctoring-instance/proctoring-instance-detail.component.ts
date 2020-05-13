import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProctoringInstance } from 'app/shared/model/proctoring-instance.model';

@Component({
  selector: 'jhi-proctoring-instance-detail',
  templateUrl: './proctoring-instance-detail.component.html'
})
export class ProctoringInstanceDetailComponent implements OnInit {
  proctoringInstance: IProctoringInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proctoringInstance }) => (this.proctoringInstance = proctoringInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
