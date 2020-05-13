import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMajorIncident } from 'app/shared/model/major-incident.model';
import { MajorIncidentService } from './major-incident.service';

@Component({
  templateUrl: './major-incident-delete-dialog.component.html'
})
export class MajorIncidentDeleteDialogComponent {
  majorIncident?: IMajorIncident;

  constructor(
    protected majorIncidentService: MajorIncidentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.majorIncidentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('majorIncidentListModification');
      this.activeModal.close();
    });
  }
}
