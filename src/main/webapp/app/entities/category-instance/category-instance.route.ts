import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICategoryInstance, CategoryInstance } from 'app/shared/model/category-instance.model';
import { CategoryInstanceService } from './category-instance.service';
import { CategoryInstanceComponent } from './category-instance.component';
import { CategoryInstanceDetailComponent } from './category-instance-detail.component';
import { CategoryInstanceUpdateComponent } from './category-instance-update.component';

@Injectable({ providedIn: 'root' })
export class CategoryInstanceResolve implements Resolve<ICategoryInstance> {
  constructor(private service: CategoryInstanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICategoryInstance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((categoryInstance: HttpResponse<CategoryInstance>) => {
          if (categoryInstance.body) {
            return of(categoryInstance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CategoryInstance());
  }
}

export const categoryInstanceRoute: Routes = [
  {
    path: '',
    component: CategoryInstanceComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.categoryInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CategoryInstanceDetailComponent,
    resolve: {
      categoryInstance: CategoryInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.categoryInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CategoryInstanceUpdateComponent,
    resolve: {
      categoryInstance: CategoryInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.categoryInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CategoryInstanceUpdateComponent,
    resolve: {
      categoryInstance: CategoryInstanceResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.categoryInstance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
