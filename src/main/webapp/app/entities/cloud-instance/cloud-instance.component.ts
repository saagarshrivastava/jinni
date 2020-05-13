import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICloudInstance } from 'app/shared/model/cloud-instance.model';
import { CloudInstanceService } from './cloud-instance.service';
import { CloudInstanceDeleteDialogComponent } from './cloud-instance-delete-dialog.component';

@Component({
  selector: 'jhi-cloud-instance',
  templateUrl: './cloud-instance.component.html'
})
export class CloudInstanceComponent implements OnInit, OnDestroy {
  cloudInstances?: ICloudInstance[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected cloudInstanceService: CloudInstanceService,
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
      this.cloudInstanceService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICloudInstance[]>) => (this.cloudInstances = res.body || []));
      return;
    }

    this.cloudInstanceService.query().subscribe((res: HttpResponse<ICloudInstance[]>) => (this.cloudInstances = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCloudInstances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICloudInstance): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCloudInstances(): void {
    this.eventSubscriber = this.eventManager.subscribe('cloudInstanceListModification', () => this.loadAll());
  }

  delete(cloudInstance: ICloudInstance): void {
    const modalRef = this.modalService.open(CloudInstanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cloudInstance = cloudInstance;
  }
}
