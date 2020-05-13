import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMajorIncidentSource, MajorIncidentSource } from 'app/shared/model/major-incident-source.model';
import { MajorIncidentSourceService } from './major-incident-source.service';

@Component({
  selector: 'jhi-major-incident-source-update',
  templateUrl: './major-incident-source-update.component.html'
})
export class MajorIncidentSourceUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(
    protected majorIncidentSourceService: MajorIncidentSourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ majorIncidentSource }) => {
      this.updateForm(majorIncidentSource);
    });
  }

  updateForm(majorIncidentSource: IMajorIncidentSource): void {
    this.editForm.patchValue({
      id: majorIncidentSource.id,
      code: majorIncidentSource.code,
      description: majorIncidentSource.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const majorIncidentSource = this.createFromForm();
    if (majorIncidentSource.id !== undefined) {
      this.subscribeToSaveResponse(this.majorIncidentSourceService.update(majorIncidentSource));
    } else {
      this.subscribeToSaveResponse(this.majorIncidentSourceService.create(majorIncidentSource));
    }
  }

  private createFromForm(): IMajorIncidentSource {
    return {
      ...new MajorIncidentSource(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMajorIncidentSource>>): void {
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
