import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProctor } from 'app/shared/model/proctor.model';
import { ProctorService } from './proctor.service';

@Component({
  templateUrl: './proctor-delete-dialog.component.html'
})
export class ProctorDeleteDialogComponent {
  proctor?: IProctor;

  constructor(protected proctorService: ProctorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.proctorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('proctorListModification');
      this.activeModal.close();
    });
  }
}
