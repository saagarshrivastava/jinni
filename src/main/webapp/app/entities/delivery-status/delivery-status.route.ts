import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeliveryStatus, DeliveryStatus } from 'app/shared/model/delivery-status.model';
import { DeliveryStatusService } from './delivery-status.service';
import { DeliveryStatusComponent } from './delivery-status.component';
import { DeliveryStatusDetailComponent } from './delivery-status-detail.component';
import { DeliveryStatusUpdateComponent } from './delivery-status-update.component';

@Injectable({ providedIn: 'root' })
export class DeliveryStatusResolve implements Resolve<IDeliveryStatus> {
  constructor(private service: DeliveryStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeliveryStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deliveryStatus: HttpResponse<DeliveryStatus>) => {
          if (deliveryStatus.body) {
            return of(deliveryStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeliveryStatus());
  }
}

export const deliveryStatusRoute: Routes = [
  {
    path: '',
    component: DeliveryStatusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeliveryStatusDetailComponent,
    resolve: {
      deliveryStatus: DeliveryStatusResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeliveryStatusUpdateComponent,
    resolve: {
      deliveryStatus: DeliveryStatusResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeliveryStatusUpdateComponent,
    resolve: {
      deliveryStatus: DeliveryStatusResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
