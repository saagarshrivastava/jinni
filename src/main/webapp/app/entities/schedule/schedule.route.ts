import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';
import { ScheduleComponent } from './schedule.component';
import { ScheduleDetailComponent } from './schedule-detail.component';
import { ScheduleUpdateComponent } from './schedule-update.component';

@Injectable({ providedIn: 'root' })
export class ScheduleResolve implements Resolve<ISchedule> {
  constructor(private service: ScheduleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISchedule> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((schedule: HttpResponse<Schedule>) => {
          if (schedule.body) {
            return of(schedule.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Schedule());
  }
}

export const scheduleRoute: Routes = [
  {
    path: '',
    component: ScheduleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScheduleDetailComponent,
    resolve: {
      schedule: ScheduleResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScheduleUpdateComponent,
    resolve: {
      schedule: ScheduleResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScheduleUpdateComponent,
    resolve: {
      schedule: ScheduleResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.schedule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
