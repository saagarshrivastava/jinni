import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDeliveryType } from 'app/shared/model/delivery-type.model';
import { DeliveryTypeService } from './delivery-type.service';

@Component({
  templateUrl: './delivery-type-delete-dialog.component.html'
})
export class DeliveryTypeDeleteDialogComponent {
  deliveryType?: IDeliveryType;

  constructor(
    protected deliveryTypeService: DeliveryTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.deliveryTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('deliveryTypeListModification');
      this.activeModal.close();
    });
  }
}
