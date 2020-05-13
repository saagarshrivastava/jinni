import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISessionBreaks } from 'app/shared/model/session-breaks.model';
import { SessionBreaksService } from './session-breaks.service';

@Component({
  templateUrl: './session-breaks-delete-dialog.component.html'
})
export class SessionBreaksDeleteDialogComponent {
  sessionBreaks?: ISessionBreaks;

  constructor(
    protected sessionBreaksService: SessionBreaksService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sessionBreaksService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sessionBreaksListModification');
      this.activeModal.close();
    });
  }
}
