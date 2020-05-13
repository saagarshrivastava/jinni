import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICloudRegion } from 'app/shared/model/cloud-region.model';
import { CloudRegionService } from './cloud-region.service';

@Component({
  templateUrl: './cloud-region-delete-dialog.component.html'
})
export class CloudRegionDeleteDialogComponent {
  cloudRegion?: ICloudRegion;

  constructor(
    protected cloudRegionService: CloudRegionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cloudRegionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cloudRegionListModification');
      this.activeModal.close();
    });
  }
}
