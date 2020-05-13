import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubcategory } from 'app/shared/model/subcategory.model';
import { SubcategoryService } from './subcategory.service';

@Component({
  templateUrl: './subcategory-delete-dialog.component.html'
})
export class SubcategoryDeleteDialogComponent {
  subcategory?: ISubcategory;

  constructor(
    protected subcategoryService: SubcategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subcategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subcategoryListModification');
      this.activeModal.close();
    });
  }
}
