import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMajorIncidentSource } from 'app/shared/model/major-incident-source.model';
import { MajorIncidentSourceService } from './major-incident-source.service';

@Component({
  templateUrl: './major-incident-source-delete-dialog.component.html'
})
export class MajorIncidentSourceDeleteDialogComponent {
  majorIncidentSource?: IMajorIncidentSource;

  constructor(
    protected majorIncidentSourceService: MajorIncidentSourceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.majorIncidentSourceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('majorIncidentSourceListModification');
      this.activeModal.close();
    });
  }
}
