import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProctoringInstance, ProctoringInstance } from 'app/shared/model/proctoring-instance.model';
import { ProctoringInstanceService } from './proctoring-instance.service';
import { ProctoringInstanceComponent } from './proctoring-instance.component';
import { ProctoringInstanceDetailComponent } from './proctoring-instance-detail.component';
import { ProctoringInstanceUpdateComponent } from './proctoring-instance-update.component';

@Injectable({ providedIn: 'root' })
export class ProctoringInstanceResolve implements Resolve<IProctoringInstance> {
  constructor(private service: ProctoringInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProctoringInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proctoringInstance: HttpResponse<ProctoringInstance>) => {
          if (proctoringInstance.body) {
            return of(proctoringInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProctoringInstance());
  }
}

export const proctoringInstanceRoute: Routes = [
  {
    path: '',
    component: ProctoringInstanceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctoringInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProctoringInstanceDetailComponent,
    resolve: {
      proctoringInstance: ProctoringInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctoringInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProctoringInstanceUpdateComponent,
    resolve: {
      proctoringInstance: ProctoringInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctoringInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProctoringInstanceUpdateComponent,
    resolve: {
      proctoringInstance: ProctoringInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctoringInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
