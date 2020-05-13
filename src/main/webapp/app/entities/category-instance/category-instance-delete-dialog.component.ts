import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategoryInstance } from 'app/shared/model/category-instance.model';
import { CategoryInstanceService } from './category-instance.service';

@Component({
  templateUrl: './category-instance-delete-dialog.component.html'
})
export class CategoryInstanceDeleteDialogComponent {
  categoryInstance?: ICategoryInstance;

  constructor(
    protected categoryInstanceService: CategoryInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categoryInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categoryInstanceListModification');
      this.activeModal.close();
    });
  }
}
