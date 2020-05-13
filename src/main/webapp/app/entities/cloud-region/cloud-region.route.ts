import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICloudRegion, CloudRegion } from 'app/shared/model/cloud-region.model';
import { CloudRegionService } from './cloud-region.service';
import { CloudRegionComponent } from './cloud-region.component';
import { CloudRegionDetailComponent } from './cloud-region-detail.component';
import { CloudRegionUpdateComponent } from './cloud-region-update.component';

@Injectable({ providedIn: 'root' })
export class CloudRegionResolve implements Resolve<ICloudRegion> {
  constructor(private service: CloudRegionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICloudRegion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((cloudRegion: HttpResponse<CloudRegion>) => {
          if (cloudRegion.body) {
            return of(cloudRegion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CloudRegion());
  }
}

export const cloudRegionRoute: Routes = [
  {
    path: '',
    component: CloudRegionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudRegion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CloudRegionDetailComponent,
    resolve: {
      cloudRegion: CloudRegionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudRegion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CloudRegionUpdateComponent,
    resolve: {
      cloudRegion: CloudRegionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudRegion.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CloudRegionUpdateComponent,
    resolve: {
      cloudRegion: CloudRegionResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.cloudRegion.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
