import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICloudRegion } from 'app/shared/model/cloud-region.model';
import { CloudRegionService } from './cloud-region.service';
import { CloudRegionDeleteDialogComponent } from './cloud-region-delete-dialog.component';

@Component({
  selector: 'jhi-cloud-region',
  templateUrl: './cloud-region.component.html'
})
export class CloudRegionComponent implements OnInit, OnDestroy {
  cloudRegions?: ICloudRegion[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected cloudRegionService: CloudRegionService,
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
      this.cloudRegionService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICloudRegion[]>) => (this.cloudRegions = res.body || []));
      return;
    }

    this.cloudRegionService.query().subscribe((res: HttpResponse<ICloudRegion[]>) => (this.cloudRegions = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCloudRegions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICloudRegion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCloudRegions(): void {
    this.eventSubscriber = this.eventManager.subscribe('cloudRegionListModification', () => this.loadAll());
  }

  delete(cloudRegion: ICloudRegion): void {
    const modalRef = this.modalService.open(CloudRegionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cloudRegion = cloudRegion;
  }
}
