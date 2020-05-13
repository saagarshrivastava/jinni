import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';
import { DeliveryStatusService } from './delivery-status.service';
import { DeliveryStatusDeleteDialogComponent } from './delivery-status-delete-dialog.component';

@Component({
  selector: 'jhi-delivery-status',
  templateUrl: './delivery-status.component.html'
})
export class DeliveryStatusComponent implements OnInit, OnDestroy {
  deliveryStatuses?: IDeliveryStatus[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected deliveryStatusService: DeliveryStatusService,
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
      this.deliveryStatusService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IDeliveryStatus[]>) => (this.deliveryStatuses = res.body || []));
      return;
    }

    this.deliveryStatusService.query().subscribe((res: HttpResponse<IDeliveryStatus[]>) => (this.deliveryStatuses = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDeliveryStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDeliveryStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDeliveryStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('deliveryStatusListModification', () => this.loadAll());
  }

  delete(deliveryStatus: IDeliveryStatus): void {
    const modalRef = this.modalService.open(DeliveryStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.deliveryStatus = deliveryStatus;
  }
}
