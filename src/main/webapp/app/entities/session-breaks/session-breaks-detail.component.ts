import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISessionBreaks } from 'app/shared/model/session-breaks.model';

@Component({
  selector: 'jhi-session-breaks-detail',
  templateUrl: './session-breaks-detail.component.html'
})
export class SessionBreaksDetailComponent implements OnInit {
  sessionBreaks: ISessionBreaks | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sessionBreaks }) => (this.sessionBreaks = sessionBreaks));
  }

  previousState(): void {
    window.history.back();
  }
}
