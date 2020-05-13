import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProctoringInstance } from 'app/shared/model/proctoring-instance.model';
import { ProctoringInstanceService } from './proctoring-instance.service';

@Component({
  templateUrl: './proctoring-instance-delete-dialog.component.html'
})
export class ProctoringInstanceDeleteDialogComponent {
  proctoringInstance?: IProctoringInstance;

  constructor(
    protected proctoringInstanceService: ProctoringInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proctoringInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proctoringInstanceListModification');
      this.activeModal.close();
    });
  }
}
