import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProctor, Proctor } from 'app/shared/model/proctor.model';
import { ProctorService } from './proctor.service';

@Component({
  selector: 'jhi-proctor-update',
  templateUrl: './proctor-update.component.html'
})
export class ProctorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    office: []
  });

  constructor(protected proctorService: ProctorService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proctor }) => {
      this.updateForm(proctor);
    });
  }

  updateForm(proctor: IProctor): void {
    this.editForm.patchValue({
      id: proctor.id,
      name: proctor.name,
      email: proctor.email,
      office: proctor.office
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proctor = this.createFromForm();
    if (proctor.id !== undefined) {
      this.subscribeToSaveResponse(this.proctorService.update(proctor));
    } else {
      this.subscribeToSaveResponse(this.proctorService.create(proctor));
    }
  }

  private createFromForm(): IProctor {
    return {
      ...new Proctor(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      office: this.editForm.get(['office'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProctor>>): void {
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
