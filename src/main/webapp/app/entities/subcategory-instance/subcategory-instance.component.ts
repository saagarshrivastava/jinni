import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubcategoryInstance } from 'app/shared/model/subcategory-instance.model';
import { SubcategoryInstanceService } from './subcategory-instance.service';
import { SubcategoryInstanceDeleteDialogComponent } from './subcategory-instance-delete-dialog.component';

@Component({
  selector: 'jhi-subcategory-instance',
  templateUrl: './subcategory-instance.component.html'
})
export class SubcategoryInstanceComponent implements OnInit, OnDestroy {
  subcategoryInstances?: ISubcategoryInstance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected subcategoryInstanceService: SubcategoryInstanceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.subcategoryInstanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISubcategoryInstance[]>) => (this.subcategoryInstances = res.body || []));
      return;
    }

    this.subcategoryInstanceService
      .query()
      .subscribe((res: HttpResponse<ISubcategoryInstance[]>) => (this.subcategoryInstances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSubcategoryInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISubcategoryInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSubcategoryInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('subcategoryInstanceListModification', () => this.loadAll());
  }

  delete(subcategoryInstance: ISubcategoryInstance): void {
    const modalRef = this.modalService.open(SubcategoryInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.subcategoryInstance = subcategoryInstance;
  }
}
