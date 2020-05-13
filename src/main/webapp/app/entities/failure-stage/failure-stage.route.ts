import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFailureStage, FailureStage } from 'app/shared/model/failure-stage.model';
import { FailureStageService } from './failure-stage.service';
import { FailureStageComponent } from './failure-stage.component';
import { FailureStageDetailComponent } from './failure-stage-detail.component';
import { FailureStageUpdateComponent } from './failure-stage-update.component';

@Injectable({ providedIn: 'root' })
export class FailureStageResolve implements Resolve<IFailureStage> {
  constructor(private service: FailureStageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFailureStage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((failureStage: HttpResponse<FailureStage>) => {
          if (failureStage.body) {
            return of(failureStage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FailureStage());
  }
}

export const failureStageRoute: Routes = [
  {
    path: '',
    component: FailureStageComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.failureStage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FailureStageDetailComponent,
    resolve: {
      failureStage: FailureStageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.failureStage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FailureStageUpdateComponent,
    resolve: {
      failureStage: FailureStageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.failureStage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FailureStageUpdateComponent,
    resolve: {
      failureStage: FailureStageResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.failureStage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
