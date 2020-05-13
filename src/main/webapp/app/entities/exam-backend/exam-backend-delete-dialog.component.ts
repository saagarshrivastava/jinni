import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExamBackend } from 'app/shared/model/exam-backend.model';
import { ExamBackendService } from './exam-backend.service';

@Component({
  templateUrl: './exam-backend-delete-dialog.component.html'
})
export class ExamBackendDeleteDialogComponent {
  examBackend?: IExamBackend;

  constructor(
    protected examBackendService: ExamBackendService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.examBackendService.delete(id).subscribe(() => {
      this.eventManager.broadcast('examBackendListModification');
      this.activeModal.close();
    });
  }
}
