import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupportInstance } from 'app/shared/model/support-instance.model';
import { SupportInstanceService } from './support-instance.service';

@Component({
  templateUrl: './support-instance-delete-dialog.component.html'
})
export class SupportInstanceDeleteDialogComponent {
  supportInstance?: ISupportInstance;

  constructor(
    protected supportInstanceService: SupportInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supportInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('supportInstanceListModification');
      this.activeModal.close();
    });
  }
}
