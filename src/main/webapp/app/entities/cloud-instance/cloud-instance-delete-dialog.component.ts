import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICloudInstance } from 'app/shared/model/cloud-instance.model';
import { CloudInstanceService } from './cloud-instance.service';

@Component({
  templateUrl: './cloud-instance-delete-dialog.component.html'
})
export class CloudInstanceDeleteDialogComponent {
  cloudInstance?: ICloudInstance;

  constructor(
    protected cloudInstanceService: CloudInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.cloudInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('cloudInstanceListModification');
      this.activeModal.close();
    });
  }
}
