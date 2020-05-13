import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferType } from 'app/shared/model/offer-type.model';
import { OfferTypeService } from './offer-type.service';

@Component({
  templateUrl: './offer-type-delete-dialog.component.html'
})
export class OfferTypeDeleteDialogComponent {
  offerType?: IOfferType;

  constructor(protected offerTypeService: OfferTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.offerTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('offerTypeListModification');
      this.activeModal.close();
    });
  }
}
