import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';

@Component({
  templateUrl: './incident-delete-dialog.component.html'
})
export class IncidentDeleteDialogComponent {
  incident?: IIncident;

  constructor(protected incidentService: IncidentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.incidentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('incidentListModification');
      this.activeModal.close();
    });
  }
}
