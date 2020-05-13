import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOffer } from 'app/shared/model/offer.model';
import { OfferService } from './offer.service';
import { OfferDeleteDialogComponent } from './offer-delete-dialog.component';

@Component({
  selector: 'jhi-offer',
  templateUrl: './offer.component.html'
})
export class OfferComponent implements OnInit, OnDestroy {
  offers?: IOffer[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected offerService: OfferService,
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
      this.offerService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));
      return;
    }

    this.offerService.query().subscribe((res: HttpResponse<IOffer[]>) => (this.offers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOffers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOffer): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOffers(): void {
    this.eventSubscriber = this.eventManager.subscribe('offerListModification', () => this.loadAll());
  }

  delete(offer: IOffer): void {
    const modalRef = this.modalService.open(OfferDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.offer = offer;
  }
}
