import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISupportInstance, SupportInstance } from 'app/shared/model/support-instance.model';
import { SupportInstanceService } from './support-instance.service';
import { SupportInstanceComponent } from './support-instance.component';
import { SupportInstanceDetailComponent } from './support-instance-detail.component';
import { SupportInstanceUpdateComponent } from './support-instance-update.component';

@Injectable({ providedIn: 'root' })
export class SupportInstanceResolve implements Resolve<ISupportInstance> {
  constructor(private service: SupportInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupportInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((supportInstance: HttpResponse<SupportInstance>) => {
          if (supportInstance.body) {
            return of(supportInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SupportInstance());
  }
}

export const supportInstanceRoute: Routes = [
  {
    path: '',
    component: SupportInstanceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupportInstanceDetailComponent,
    resolve: {
      supportInstance: SupportInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupportInstanceUpdateComponent,
    resolve: {
      supportInstance: SupportInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupportInstanceUpdateComponent,
    resolve: {
      supportInstance: SupportInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
