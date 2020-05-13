import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISubcategory, Subcategory } from 'app/shared/model/subcategory.model';
import { SubcategoryService } from './subcategory.service';
import { SubcategoryComponent } from './subcategory.component';
import { SubcategoryDetailComponent } from './subcategory-detail.component';
import { SubcategoryUpdateComponent } from './subcategory-update.component';

@Injectable({ providedIn: 'root' })
export class SubcategoryResolve implements Resolve<ISubcategory> {
  constructor(private service: SubcategoryService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubcategory> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((subcategory: HttpResponse<Subcategory>) => {
          if (subcategory.body) {
            return of(subcategory.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Subcategory());
  }
}

export const subcategoryRoute: Routes = [
  {
    path: '',
    component: SubcategoryComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.subcategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubcategoryDetailComponent,
    resolve: {
      subcategory: SubcategoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.subcategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubcategoryUpdateComponent,
    resolve: {
      subcategory: SubcategoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.subcategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubcategoryUpdateComponent,
    resolve: {
      subcategory: SubcategoryResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jinniApp.subcategory.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
