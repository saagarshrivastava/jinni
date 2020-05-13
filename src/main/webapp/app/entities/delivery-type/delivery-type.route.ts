import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDeliveryType, DeliveryType } from 'app/shared/model/delivery-type.model';
import { DeliveryTypeService } from './delivery-type.service';
import { DeliveryTypeComponent } from './delivery-type.component';
import { DeliveryTypeDetailComponent } from './delivery-type-detail.component';
import { DeliveryTypeUpdateComponent } from './delivery-type-update.component';

@Injectable({ providedIn: 'root' })
export class DeliveryTypeResolve implements Resolve<IDeliveryType> {
  constructor(private service: DeliveryTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDeliveryType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((deliveryType: HttpResponse<DeliveryType>) => {
          if (deliveryType.body) {
            return of(deliveryType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new DeliveryType());
  }
}

export const deliveryTypeRoute: Routes = [
  {
    path: '',
    component: DeliveryTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DeliveryTypeDetailComponent,
    resolve: {
      deliveryType: DeliveryTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DeliveryTypeUpdateComponent,
    resolve: {
      deliveryType: DeliveryTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DeliveryTypeUpdateComponent,
    resolve: {
      deliveryType: DeliveryTypeResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.deliveryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
