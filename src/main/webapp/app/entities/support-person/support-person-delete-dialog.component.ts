import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISupportPerson } from 'app/shared/model/support-person.model';
import { SupportPersonService } from './support-person.service';

@Component({
  templateUrl: './support-person-delete-dialog.component.html'
})
export class SupportPersonDeleteDialogComponent {
  supportPerson?: ISupportPerson;

  constructor(
    protected supportPersonService: SupportPersonService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.supportPersonService.delete(id).subscribe(() => {
      this.eventManager.broadcast('supportPersonListModification');
      this.activeModal.close();
    });
  }
}
