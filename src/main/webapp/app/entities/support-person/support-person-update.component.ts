import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISupportPerson, SupportPerson } from 'app/shared/model/support-person.model';
import { SupportPersonService } from './support-person.service';

@Component({
  selector: 'jhi-support-person-update',
  templateUrl: './support-person-update.component.html'
})
export class SupportPersonUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    email: [],
    office: []
  });

  constructor(protected supportPersonService: SupportPersonService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ supportPerson }) => {
      this.updateForm(supportPerson);
    });
  }

  updateForm(supportPerson: ISupportPerson): void {
    this.editForm.patchValue({
      id: supportPerson.id,
      name: supportPerson.name,
      email: supportPerson.email,
      office: supportPerson.office
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const supportPerson = this.createFromForm();
    if (supportPerson.id !== undefined) {
      this.subscribeToSaveResponse(this.supportPersonService.update(supportPerson));
    } else {
      this.subscribeToSaveResponse(this.supportPersonService.create(supportPerson));
    }
  }

  private createFromForm(): ISupportPerson {
    return {
      ...new SupportPerson(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      email: this.editForm.get(['email'])!.value,
      office: this.editForm.get(['office'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISupportPerson>>): void {
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
