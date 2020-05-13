import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IIncident, Incident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { IncidentComponent } from './incident.component';
import { IncidentDetailComponent } from './incident-detail.component';
import { IncidentUpdateComponent } from './incident-update.component';

@Injectable({ providedIn: 'root' })
export class IncidentResolve implements Resolve<IIncident> {
  constructor(private service: IncidentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IIncident> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((incident: HttpResponse<Incident>) => {
          if (incident.body) {
            return of(incident.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Incident());
  }
}

export const incidentRoute: Routes = [
  {
    path: '',
    component: IncidentComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.incident.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncidentDetailComponent,
    resolve: {
      incident: IncidentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.incident.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IncidentUpdateComponent,
    resolve: {
      incident: IncidentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.incident.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IncidentUpdateComponent,
    resolve: {
      incident: IncidentResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.incident.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
