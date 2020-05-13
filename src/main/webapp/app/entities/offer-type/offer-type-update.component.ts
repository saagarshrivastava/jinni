import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOfferType, OfferType } from 'app/shared/model/offer-type.model';
import { OfferTypeService } from './offer-type.service';

@Component({
  selector: 'jhi-offer-type-update',
  templateUrl: './offer-type-update.component.html'
})
export class OfferTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(protected offerTypeService: OfferTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerType }) => {
      this.updateForm(offerType);
    });
  }

  updateForm(offerType: IOfferType): void {
    this.editForm.patchValue({
      id: offerType.id,
      code: offerType.code,
      description: offerType.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const offerType = this.createFromForm();
    if (offerType.id !== undefined) {
      this.subscribeToSaveResponse(this.offerTypeService.update(offerType));
    } else {
      this.subscribeToSaveResponse(this.offerTypeService.create(offerType));
    }
  }

  private createFromForm(): IOfferType {
    return {
      ...new OfferType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfferType>>): void {
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
}
