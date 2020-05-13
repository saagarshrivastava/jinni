import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProctor, Proctor } from 'app/shared/model/proctor.model';
import { ProctorService } from './proctor.service';
import { ProctorComponent } from './proctor.component';
import { ProctorDetailComponent } from './proctor-detail.component';
import { ProctorUpdateComponent } from './proctor-update.component';

@Injectable({ providedIn: 'root' })
export class ProctorResolve implements Resolve<IProctor> {
  constructor(private service: ProctorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProctor> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((proctor: HttpResponse<Proctor>) => {
          if (proctor.body) {
            return of(proctor.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Proctor());
  }
}

export const proctorRoute: Routes = [
  {
    path: '',
    component: ProctorComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProctorDetailComponent,
    resolve: {
      proctor: ProctorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProctorUpdateComponent,
    resolve: {
      proctor: ProctorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProctorUpdateComponent,
    resolve: {
      proctor: ProctorResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.proctor.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
