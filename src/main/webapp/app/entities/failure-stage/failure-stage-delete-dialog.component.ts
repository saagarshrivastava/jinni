import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFailureStage } from 'app/shared/model/failure-stage.model';
import { FailureStageService } from './failure-stage.service';

@Component({
  templateUrl: './failure-stage-delete-dialog.component.html'
})
export class FailureStageDeleteDialogComponent {
  failureStage?: IFailureStage;

  constructor(
    protected failureStageService: FailureStageService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.failureStageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('failureStageListModification');
      this.activeModal.close();
    });
  }
}
