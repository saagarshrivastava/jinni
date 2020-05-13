import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMajorIncident } from 'app/shared/model/major-incident.model';
import { MajorIncidentService } from './major-incident.service';
import { MajorIncidentDeleteDialogComponent } from './major-incident-delete-dialog.component';

@Component({
  selector: 'jhi-major-incident',
  templateUrl: './major-incident.component.html'
})
export class MajorIncidentComponent implements OnInit, OnDestroy {
  majorIncidents?: IMajorIncident[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected majorIncidentService: MajorIncidentService,
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
      this.majorIncidentService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IMajorIncident[]>) => (this.majorIncidents = res.body || []));
      return;
    }

    this.majorIncidentService.query().subscribe((res: HttpResponse<IMajorIncident[]>) => (this.majorIncidents = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMajorIncidents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMajorIncident): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMajorIncidents(): void {
    this.eventSubscriber = this.eventManager.subscribe('majorIncidentListModification', () => this.loadAll());
  }

  delete(majorIncident: IMajorIncident): void {
    const modalRef = this.modalService.open(MajorIncidentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.majorIncident = majorIncident;
  }
}
