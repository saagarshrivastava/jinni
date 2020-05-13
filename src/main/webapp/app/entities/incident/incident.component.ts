import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { IncidentDeleteDialogComponent } from './incident-delete-dialog.component';

@Component({
  selector: 'jhi-incident',
  templateUrl: './incident.component.html'
})
export class IncidentComponent implements OnInit, OnDestroy {
  incidents?: IIncident[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected incidentService: IncidentService,
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
      this.incidentService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IIncident[]>) => (this.incidents = res.body || []));
      return;
    }

    this.incidentService.query().subscribe((res: HttpResponse<IIncident[]>) => (this.incidents = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInIncidents();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IIncident): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInIncidents(): void {
    this.eventSubscriber = this.eventManager.subscribe('incidentListModification', () => this.loadAll());
  }

  delete(incident: IIncident): void {
    const modalRef = this.modalService.open(IncidentDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.incident = incident;
  }
}
