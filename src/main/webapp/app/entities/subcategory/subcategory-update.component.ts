import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISubcategory, Subcategory } from 'app/shared/model/subcategory.model';
import { SubcategoryService } from './subcategory.service';
import { ICategory } from 'app/shared/model/category.model';
import { CategoryService } from 'app/entities/category/category.service';

@Component({
  selector: 'jhi-subcategory-update',
  templateUrl: './subcategory-update.component.html'
})
export class SubcategoryUpdateComponent implements OnInit {
  isSaving = false;
  categories: ICategory[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    description: [],
    categoryid: [],
    categoryid: []
  });

  constructor(
    protected subcategoryService: SubcategoryService,
    protected categoryService: CategoryService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subcategory }) => {
      this.updateForm(subcategory);

      this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => (this.categories = res.body || []));
    });
  }

  updateForm(subcategory: ISubcategory): void {
    this.editForm.patchValue({
      id: subcategory.id,
      code: subcategory.code,
      description: subcategory.description,
      categoryid: subcategory.categoryid,
      categoryid: subcategory.categoryid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const subcategory = this.createFromForm();
    if (subcategory.id !== undefined) {
      this.subscribeToSaveResponse(this.subcategoryService.update(subcategory));
    } else {
      this.subscribeToSaveResponse(this.subcategoryService.create(subcategory));
    }
  }

  private createFromForm(): ISubcategory {
    return {
      ...new Subcategory(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value,
      categoryid: this.editForm.get(['categoryid'])!.value,
      categoryid: this.editForm.get(['categoryid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubcategory>>): void {
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

  trackById(index: number, item: ICategory): any {
    return item.id;
  }
}
