import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategoryInstance } from 'app/shared/model/category-instance.model';
import { CategoryInstanceService } from './category-instance.service';
import { CategoryInstanceDeleteDialogComponent } from './category-instance-delete-dialog.component';

@Component({
  selector: 'jhi-category-instance',
  templateUrl: './category-instance.component.html'
})
export class CategoryInstanceComponent implements OnInit, OnDestroy {
  categoryInstances?: ICategoryInstance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected categoryInstanceService: CategoryInstanceService,
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
      this.categoryInstanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICategoryInstance[]>) => (this.categoryInstances = res.body || []));
      return;
    }

    this.categoryInstanceService.query().subscribe((res: HttpResponse<ICategoryInstance[]>) => (this.categoryInstances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategoryInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategoryInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategoryInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('categoryInstanceListModification', () => this.loadAll());
  }

  delete(categoryInstance: ICategoryInstance): void {
    const modalRef = this.modalService.open(CategoryInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categoryInstance = categoryInstance;
  }
}
