import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';
import { DeliveryStatusService } from './delivery-status.service';

@Component({
  templateUrl: './delivery-status-delete-dialog.component.html'
})
export class DeliveryStatusDeleteDialogComponent {
  deliveryStatus?: IDeliveryStatus;

  constructor(
    protected deliveryStatusService: DeliveryStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deliveryStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deliveryStatusListModification');
      this.activeModal.close();
    });
  }
}
