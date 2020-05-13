import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISessionBreaks, SessionBreaks } from 'app/shared/model/session-breaks.model';
import { SessionBreaksService } from './session-breaks.service';
import { SessionBreaksComponent } from './session-breaks.component';
import { SessionBreaksDetailComponent } from './session-breaks-detail.component';
import { SessionBreaksUpdateComponent } from './session-breaks-update.component';

@Injectable({ providedIn: 'root' })
export class SessionBreaksResolve implements Resolve<ISessionBreaks> {
  constructor(private service: SessionBreaksService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISessionBreaks> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sessionBreaks: HttpResponse<SessionBreaks>) => {
          if (sessionBreaks.body) {
            return of(sessionBreaks.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SessionBreaks());
  }
}

export const sessionBreaksRoute: Routes = [
  {
    path: '',
    component: SessionBreaksComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.sessionBreaks.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SessionBreaksDetailComponent,
    resolve: {
      sessionBreaks: SessionBreaksResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.sessionBreaks.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SessionBreaksUpdateComponent,
    resolve: {
      sessionBreaks: SessionBreaksResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.sessionBreaks.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SessionBreaksUpdateComponent,
    resolve: {
      sessionBreaks: SessionBreaksResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.sessionBreaks.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
