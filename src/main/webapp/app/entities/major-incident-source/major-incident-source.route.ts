import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMajorIncidentSource, MajorIncidentSource } from 'app/shared/model/major-incident-source.model';
import { MajorIncidentSourceService } from './major-incident-source.service';
import { MajorIncidentSourceComponent } from './major-incident-source.component';
import { MajorIncidentSourceDetailComponent } from './major-incident-source-detail.component';
import { MajorIncidentSourceUpdateComponent } from './major-incident-source-update.component';

@Injectable({ providedIn: 'root' })
export class MajorIncidentSourceResolve implements Resolve<IMajorIncidentSource> {
  constructor(private service: MajorIncidentSourceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMajorIncidentSource> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((majorIncidentSource: HttpResponse<MajorIncidentSource>) => {
          if (majorIncidentSource.body) {
            return of(majorIncidentSource.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MajorIncidentSource());
  }
}

export const majorIncidentSourceRoute: Routes = [
  {
    path: '',
    component: MajorIncidentSourceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncidentSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MajorIncidentSourceDetailComponent,
    resolve: {
      majorIncidentSource: MajorIncidentSourceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncidentSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MajorIncidentSourceUpdateComponent,
    resolve: {
      majorIncidentSource: MajorIncidentSourceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncidentSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MajorIncidentSourceUpdateComponent,
    resolve: {
      majorIncidentSource: MajorIncidentSourceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncidentSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
