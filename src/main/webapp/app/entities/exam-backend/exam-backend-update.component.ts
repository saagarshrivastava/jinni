import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IExamBackend, ExamBackend } from 'app/shared/model/exam-backend.model';
import { ExamBackendService } from './exam-backend.service';

@Component({
  selector: 'jhi-exam-backend-update',
  templateUrl: './exam-backend-update.component.html'
})
export class ExamBackendUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    code: [],
    description: []
  });

  constructor(protected examBackendService: ExamBackendService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ examBackend }) => {
      this.updateForm(examBackend);
    });
  }

  updateForm(examBackend: IExamBackend): void {
    this.editForm.patchValue({
      id: examBackend.id,
      code: examBackend.code,
      description: examBackend.description
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const examBackend = this.createFromForm();
    if (examBackend.id !== undefined) {
      this.subscribeToSaveResponse(this.examBackendService.update(examBackend));
    } else {
      this.subscribeToSaveResponse(this.examBackendService.create(examBackend));
    }
  }

  private createFromForm(): IExamBackend {
    return {
      ...new ExamBackend(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      description: this.editForm.get(['description'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExamBackend>>): void {
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
