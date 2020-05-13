import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMajorIncident, MajorIncident } from 'app/shared/model/major-incident.model';
import { MajorIncidentService } from './major-incident.service';
import { MajorIncidentComponent } from './major-incident.component';
import { MajorIncidentDetailComponent } from './major-incident-detail.component';
import { MajorIncidentUpdateComponent } from './major-incident-update.component';

@Injectable({ providedIn: 'root' })
export class MajorIncidentResolve implements Resolve<IMajorIncident> {
  constructor(private service: MajorIncidentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMajorIncident> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((majorIncident: HttpResponse<MajorIncident>) => {
          if (majorIncident.body) {
            return of(majorIncident.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MajorIncident());
  }
}

export const majorIncidentRoute: Routes = [
  {
    path: '',
    component: MajorIncidentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncident.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MajorIncidentDetailComponent,
    resolve: {
      majorIncident: MajorIncidentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncident.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MajorIncidentUpdateComponent,
    resolve: {
      majorIncident: MajorIncidentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncident.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MajorIncidentUpdateComponent,
    resolve: {
      majorIncident: MajorIncidentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.majorIncident.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
