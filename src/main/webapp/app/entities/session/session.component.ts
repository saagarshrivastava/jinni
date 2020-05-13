import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISession } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { SessionDeleteDialogComponent } from './session-delete-dialog.component';

@Component({
  selector: 'jhi-session',
  templateUrl: './session.component.html'
})
export class SessionComponent implements OnInit, OnDestroy {
  sessions?: ISession[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected sessionService: SessionService,
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
      this.sessionService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISession[]>) => (this.sessions = res.body || []));
      return;
    }

    this.sessionService.query().subscribe((res: HttpResponse<ISession[]>) => (this.sessions = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSessions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISession): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSessions(): void {
    this.eventSubscriber = this.eventManager.subscribe('sessionListModification', () => this.loadAll());
  }

  delete(session: ISession): void {
    const modalRef = this.modalService.open(SessionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.session = session;
  }
}
