import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISchedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { ScheduleDeleteDialogComponent } from './schedule-delete-dialog.component';

@Component({
  selector: 'jhi-schedule',
  templateUrl: './schedule.component.html'
})
export class ScheduleComponent implements OnInit, OnDestroy {
  schedules?: ISchedule[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected scheduleService: ScheduleService,
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
      this.scheduleService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISchedule[]>) => (this.schedules = res.body || []));
      return;
    }

    this.scheduleService.query().subscribe((res: HttpResponse<ISchedule[]>) => (this.schedules = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSchedules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISchedule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSchedules(): void {
    this.eventSubscriber = this.eventManager.subscribe('scheduleListModification', () => this.loadAll());
  }

  delete(schedule: ISchedule): void {
    const modalRef = this.modalService.open(ScheduleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.schedule = schedule;
  }
}
