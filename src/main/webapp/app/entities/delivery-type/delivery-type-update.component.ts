import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeliveryType, DeliveryType } from 'app/shared/model/delivery-type.model';
import { DeliveryTypeService } from './delivery-type.service';

@Component({
  selector: 'jhi-delivery-type-update',
  templateUrl: './delivery-type-update.component.html'
})
export class DeliveryTypeUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(protected deliveryTypeService: DeliveryTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliveryType }) => {
      this.updateForm(deliveryType);
    });
  }

  updateForm(deliveryType: IDeliveryType): void {
    this.editForm.patchValue({
      id: deliveryType.id,
      code: deliveryType.code,
      description: deliveryType.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deliveryType = this.createFromForm();
    if (deliveryType.id !== undefined) {
      this.subscribeToSaveResponse(this.deliveryTypeService.update(deliveryType));
    } else {
      this.subscribeToSaveResponse(this.deliveryTypeService.create(deliveryType));
    }
  }

  private createFromForm(): IDeliveryType {
    return {
      ...new DeliveryType(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryType>>): void {
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
