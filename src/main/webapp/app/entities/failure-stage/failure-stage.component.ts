import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFailureStage } from 'app/shared/model/failure-stage.model';
import { FailureStageService } from './failure-stage.service';
import { FailureStageDeleteDialogComponent } from './failure-stage-delete-dialog.component';

@Component({
  selector: 'jhi-failure-stage',
  templateUrl: './failure-stage.component.html'
})
export class FailureStageComponent implements OnInit, OnDestroy {
  failureStages?: IFailureStage[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected failureStageService: FailureStageService,
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
      this.failureStageService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IFailureStage[]>) => (this.failureStages = res.body || []));
      return;
    }

    this.failureStageService.query().subscribe((res: HttpResponse<IFailureStage[]>) => (this.failureStages = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInFailureStages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IFailureStage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInFailureStages(): void {
    this.eventSubscriber = this.eventManager.subscribe('failureStageListModification', () => this.loadAll());
  }

  delete(failureStage: IFailureStage): void {
    const modalRef = this.modalService.open(FailureStageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.failureStage = failureStage;
  }
}
