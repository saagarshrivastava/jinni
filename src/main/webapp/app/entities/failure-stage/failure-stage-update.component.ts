import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFailureStage, FailureStage } from 'app/shared/model/failure-stage.model';
import { FailureStageService } from './failure-stage.service';

@Component({
  selector: 'jhi-failure-stage-update',
  templateUrl: './failure-stage-update.component.html'
})
export class FailureStageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(protected failureStageService: FailureStageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ failureStage }) => {
      this.updateForm(failureStage);
    });
  }

  updateForm(failureStage: IFailureStage): void {
    this.editForm.patchValue({
      id: failureStage.id,
      code: failureStage.code,
      description: failureStage.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const failureStage = this.createFromForm();
    if (failureStage.id !== undefined) {
      this.subscribeToSaveResponse(this.failureStageService.update(failureStage));
    } else {
      this.subscribeToSaveResponse(this.failureStageService.create(failureStage));
    }
  }

  private createFromForm(): IFailureStage {
    return {
      ...new FailureStage(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFailureStage>>): void {
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
