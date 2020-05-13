import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ICategoryInstance, CategoryInstance } from 'app/shared/model/category-instance.model';
import { CategoryInstanceService } from './category-instance.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';
import { IIncident } from 'app/shared/model/incident.model';
import { IncidentService } from 'app/entities/incident/incident.service';

type SelectableEntity = ICategory | IIncident;

@Component({
  selector: 'jhi-category-instance-update',
  templateUrl: './category-instance-update.component.html'
})
export class CategoryInstanceUpdateComponent implements OnInit {
  isSaving = false;
  categoryids: ICategory[] = [];
  incidents: IIncident[] = [];

  editForm = this.fb.group({
    id: [],
    incidentid: [],
    categoryid: [],
    rank: [],
    categoryid: [],
    incidentid: []
  });

  constructor(
    protected categoryInstanceService: CategoryInstanceService,
    protected categoryService: CategoryService,
    protected incidentService: IncidentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ categoryInstance }) => {
      this.updateForm(categoryInstance);

      this.categoryService
        .query({ filter: 'categoryinstance-is-null' })
        .pipe(
          map((res: HttpResponse<ICategory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICategory[]) => {
          if (!categoryInstance.categoryid || !categoryInstance.categoryid.id) {
            this.categoryids = resBody;
          } else {
            this.categoryService
              .find(categoryInstance.categoryid.id)
              .pipe(
                map((subRes: HttpResponse<ICategory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICategory[]) => (this.categoryids = concatRes));
          }
        });

      this.incidentService.query().subscribe((res: HttpResponse<IIncident[]>) => (this.incidents = res.body || []));
    });
  }

  updateForm(categoryInstance: ICategoryInstance): void {
    this.editForm.patchValue({
      id: categoryInstance.id,
      incidentid: categoryInstance.incidentid,
      categoryid: categoryInstance.categoryid,
      rank: categoryInstance.rank,
      categoryid: categoryInstance.categoryid,
      incidentid: categoryInstance.incidentid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const categoryInstance = this.createFromForm();
    if (categoryInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryInstanceService.update(categoryInstance));
    } else {
      this.subscribeToSaveResponse(this.categoryInstanceService.create(categoryInstance));
    }
  }

  private createFromForm(): ICategoryInstance {
    return {
      ...new CategoryInstance(),
      id: this.editForm.get(['id'])!.value,
      incidentid: this.editForm.get(['incidentid'])!.value,
      categoryid: this.editForm.get(['categoryid'])!.value,
      rank: this.editForm.get(['rank'])!.value,
      categoryid: this.editForm.get(['categoryid'])!.value,
      incidentid: this.editForm.get(['incidentid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategoryInstance>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
