import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISubcategoryInstance, SubcategoryInstance } from 'app/shared/model/subcategory-instance.model';
import { SubcategoryInstanceService } from './subcategory-instance.service';
import { ISubcategory } from 'app/shared/model/subcategory.model';
import { SubcategoryService } from 'app/entities/subcategory/subcategory.service';
import { ICategoryInstance } from 'app/shared/model/category-instance.model';
import { CategoryInstanceService } from 'app/entities/category-instance/category-instance.service';

type SelectableEntity = ISubcategory | ICategoryInstance;

@Component({
  selector: 'jhi-subcategory-instance-update',
  templateUrl: './subcategory-instance-update.component.html'
})
export class SubcategoryInstanceUpdateComponent implements OnInit {
  isSaving = false;
  subcategoryids: ISubcategory[] = [];
  categoryinstances: ICategoryInstance[] = [];

  editForm = this.fb.group({
    id: [],
    categoryinstanceid: [],
    subcategoryid: [],
    rank: [],
    subcategoryid: [],
    categoryinstanceid: []
  });

  constructor(
    protected subcategoryInstanceService: SubcategoryInstanceService,
    protected subcategoryService: SubcategoryService,
    protected categoryInstanceService: CategoryInstanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subcategoryInstance }) => {
      this.updateForm(subcategoryInstance);

      this.subcategoryService
        .query({ filter: 'subcategoryinstance-is-null' })
        .pipe(
          map((res: HttpResponse<ISubcategory[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISubcategory[]) => {
          if (!subcategoryInstance.subcategoryid || !subcategoryInstance.subcategoryid.id) {
            this.subcategoryids = resBody;
          } else {
            this.subcategoryService
              .find(subcategoryInstance.subcategoryid.id)
              .pipe(
                map((subRes: HttpResponse<ISubcategory>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISubcategory[]) => (this.subcategoryids = concatRes));
          }
        });

      this.categoryInstanceService.query().subscribe((res: HttpResponse<ICategoryInstance[]>) => (this.categoryinstances = res.body || []));
    });
  }

  updateForm(subcategoryInstance: ISubcategoryInstance): void {
    this.editForm.patchValue({
      id: subcategoryInstance.id,
      categoryinstanceid: subcategoryInstance.categoryinstanceid,
      subcategoryid: subcategoryInstance.subcategoryid,
      rank: subcategoryInstance.rank,
      subcategoryid: subcategoryInstance.subcategoryid,
      categoryinstanceid: subcategoryInstance.categoryinstanceid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subcategoryInstance = this.createFromForm();
    if (subcategoryInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.subcategoryInstanceService.update(subcategoryInstance));
    } else {
      this.subscribeToSaveResponse(this.subcategoryInstanceService.create(subcategoryInstance));
    }
  }

  private createFromForm(): ISubcategoryInstance {
    return {
      ...new SubcategoryInstance(),
      id: this.editForm.get(['id'])!.value,
      categoryinstanceid: this.editForm.get(['categoryinstanceid'])!.value,
      subcategoryid: this.editForm.get(['subcategoryid'])!.value,
      rank: this.editForm.get(['rank'])!.value,
      subcategoryid: this.editForm.get(['subcategoryid'])!.value,
      categoryinstanceid: this.editForm.get(['categoryinstanceid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubcategoryInstance>>): void {
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
