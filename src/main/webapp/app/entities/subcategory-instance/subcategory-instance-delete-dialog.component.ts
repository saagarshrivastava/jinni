import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubcategoryInstance } from 'app/shared/model/subcategory-instance.model';
import { SubcategoryInstanceService } from './subcategory-instance.service';

@Component({
  templateUrl: './subcategory-instance-delete-dialog.component.html'
})
export class SubcategoryInstanceDeleteDialogComponent {
  subcategoryInstance?: ISubcategoryInstance;

  constructor(
    protected subcategoryInstanceService: SubcategoryInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subcategoryInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subcategoryInstanceListModification');
      this.activeModal.close();
    });
  }
}
