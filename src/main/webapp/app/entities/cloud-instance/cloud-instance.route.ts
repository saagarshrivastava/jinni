import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICloudInstance, CloudInstance } from 'app/shared/model/cloud-instance.model';
import { CloudInstanceService } from './cloud-instance.service';
import { CloudInstanceComponent } from './cloud-instance.component';
import { CloudInstanceDetailComponent } from './cloud-instance-detail.component';
import { CloudInstanceUpdateComponent } from './cloud-instance-update.component';

@Injectable({ providedIn: 'root' })
export class CloudInstanceResolve implements Resolve<ICloudInstance> {
  constructor(private service: CloudInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICloudInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cloudInstance: HttpResponse<CloudInstance>) => {
          if (cloudInstance.body) {
            return of(cloudInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CloudInstance());
  }
}

export const cloudInstanceRoute: Routes = [
  {
    path: '',
    component: CloudInstanceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CloudInstanceDetailComponent,
    resolve: {
      cloudInstance: CloudInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CloudInstanceUpdateComponent,
    resolve: {
      cloudInstance: CloudInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CloudInstanceUpdateComponent,
    resolve: {
      cloudInstance: CloudInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
