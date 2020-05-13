import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISupportPerson, SupportPerson } from 'app/shared/model/support-person.model';
import { SupportPersonService } from './support-person.service';
import { SupportPersonComponent } from './support-person.component';
import { SupportPersonDetailComponent } from './support-person-detail.component';
import { SupportPersonUpdateComponent } from './support-person-update.component';

@Injectable({ providedIn: 'root' })
export class SupportPersonResolve implements Resolve<ISupportPerson> {
  constructor(private service: SupportPersonService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISupportPerson> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((supportPerson: HttpResponse<SupportPerson>) => {
          if (supportPerson.body) {
            return of(supportPerson.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SupportPerson());
  }
}

export const supportPersonRoute: Routes = [
  {
    path: '',
    component: SupportPersonComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportPerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SupportPersonDetailComponent,
    resolve: {
      supportPerson: SupportPersonResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportPerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SupportPersonUpdateComponent,
    resolve: {
      supportPerson: SupportPersonResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportPerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SupportPersonUpdateComponent,
    resolve: {
      supportPerson: SupportPersonResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.supportPerson.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
