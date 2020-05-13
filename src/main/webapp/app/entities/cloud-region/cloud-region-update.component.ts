import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICloudRegion, CloudRegion } from 'app/shared/model/cloud-region.model';
import { CloudRegionService } from './cloud-region.service';

@Component({
  selector: 'jhi-cloud-region-update',
  templateUrl: './cloud-region-update.component.html'
})
export class CloudRegionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(protected cloudRegionService: CloudRegionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cloudRegion }) => {
      this.updateForm(cloudRegion);
    });
  }

  updateForm(cloudRegion: ICloudRegion): void {
    this.editForm.patchValue({
      id: cloudRegion.id,
      code: cloudRegion.code,
      description: cloudRegion.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cloudRegion = this.createFromForm();
    if (cloudRegion.id !== undefined) {
      this.subscribeToSaveResponse(this.cloudRegionService.update(cloudRegion));
    } else {
      this.subscribeToSaveResponse(this.cloudRegionService.create(cloudRegion));
    }
  }

  private createFromForm(): ICloudRegion {
    return {
      ...new CloudRegion(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICloudRegion>>): void {
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
