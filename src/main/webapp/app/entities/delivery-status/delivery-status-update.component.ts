import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDeliveryStatus, DeliveryStatus } from 'app/shared/model/delivery-status.model';
import { DeliveryStatusService } from './delivery-status.service';

@Component({
  selector: 'jhi-delivery-status-update',
  templateUrl: './delivery-status-update.component.html'
})
export class DeliveryStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(protected deliveryStatusService: DeliveryStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliveryStatus }) => {
      this.updateForm(deliveryStatus);
    });
  }

  updateForm(deliveryStatus: IDeliveryStatus): void {
    this.editForm.patchValue({
      id: deliveryStatus.id,
      code: deliveryStatus.code,
      description: deliveryStatus.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const deliveryStatus = this.createFromForm();
    if (deliveryStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.deliveryStatusService.update(deliveryStatus));
    } else {
      this.subscribeToSaveResponse(this.deliveryStatusService.create(deliveryStatus));
    }
  }

  private createFromForm(): IDeliveryStatus {
    return {
      ...new DeliveryStatus(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDeliveryStatus>>): void {
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
