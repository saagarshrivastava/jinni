import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISessionBreaks, SessionBreaks } from 'app/shared/model/session-breaks.model';
import { SessionBreaksService } from './session-breaks.service';
import { IProctoringInstance } from 'app/shared/model/proctoring-instance.model';
import { ProctoringInstanceService } from 'app/entities/proctoring-instance/proctoring-instance.service';

@Component({
  selector: 'jhi-session-breaks-update',
  templateUrl: './session-breaks-update.component.html'
})
export class SessionBreaksUpdateComponent implements OnInit {
  isSaving = false;
  proctoringinstances: IProctoringInstance[] = [];
  starttimeDp: any;
  endtimeDp: any;

  editForm = this.fb.group({
    id: [],
    starttime: [],
    endtime: [],
    proctoringinstanceid: [],
    proctoringinstanceid: []
  });

  constructor(
    protected sessionBreaksService: SessionBreaksService,
    protected proctoringInstanceService: ProctoringInstanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sessionBreaks }) => {
      this.updateForm(sessionBreaks);

      this.proctoringInstanceService
        .query()
        .subscribe((res: HttpResponse<IProctoringInstance[]>) => (this.proctoringinstances = res.body || []));
    });
  }

  updateForm(sessionBreaks: ISessionBreaks): void {
    this.editForm.patchValue({
      id: sessionBreaks.id,
      starttime: sessionBreaks.starttime,
      endtime: sessionBreaks.endtime,
      proctoringinstanceid: sessionBreaks.proctoringinstanceid,
      proctoringinstanceid: sessionBreaks.proctoringinstanceid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sessionBreaks = this.createFromForm();
    if (sessionBreaks.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionBreaksService.update(sessionBreaks));
    } else {
      this.subscribeToSaveResponse(this.sessionBreaksService.create(sessionBreaks));
    }
  }

  private createFromForm(): ISessionBreaks {
    return {
      ...new SessionBreaks(),
      id: this.editForm.get(['id'])!.value,
      starttime: this.editForm.get(['starttime'])!.value,
      endtime: this.editForm.get(['endtime'])!.value,
      proctoringinstanceid: this.editForm.get(['proctoringinstanceid'])!.value,
      proctoringinstanceid: this.editForm.get(['proctoringinstanceid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISessionBreaks>>): void {
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

  trackById(index: number, item: IProctoringInstance): any {
    return item.id;
  }
}
