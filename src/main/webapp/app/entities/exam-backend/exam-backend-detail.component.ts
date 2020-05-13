import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExamBackend } from 'app/shared/model/exam-backend.model';

@Component({
  selector: 'jhi-exam-backend-detail',
  templateUrl: './exam-backend-detail.component.html'
})
export class ExamBackendDetailComponent implements OnInit {
  examBackend: IExamBackend | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examBackend }) => (this.examBackend = examBackend));
  }

  previousState(): void {
    window.history.back();
  }
}
