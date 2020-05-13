import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProctoringInstance, ProctoringInstance } from 'app/shared/model/proctoring-instance.model';
import { ProctoringInstanceService } from './proctoring-instance.service';
import { IProctor } from 'app/shared/model/proctor.model';
import { ProctorService } from 'app/entities/proctor/proctor.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';

type SelectableEntity = IProctor | ISession;

@Component({
  selector: 'jhi-proctoring-instance-update',
  templateUrl: './proctoring-instance-update.component.html'
})
export class ProctoringInstanceUpdateComponent implements OnInit {
  isSaving = false;
  proctors: IProctor[] = [];
  sessions: ISession[] = [];
  proctorstarttimeDp: any;
  proctorendtimeDp: any;

  editForm = this.fb.group({
    id: [],
    proctorstarttime: [],
    proctorendtime: [],
    proctorid: [],
    sessionid: [],
    sessionnotes: [],
    proctorchat: [],
    suspended: [],
    terminated: [],
    numberofbreaks: [],
    proctorid: [],
    sessionid: []
  });

  constructor(
    protected proctoringInstanceService: ProctoringInstanceService,
    protected proctorService: ProctorService,
    protected sessionService: SessionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ proctoringInstance }) => {
      this.updateForm(proctoringInstance);

      this.proctorService.query().subscribe((res: HttpResponse<IProctor[]>) => (this.proctors = res.body || []));

      this.sessionService.query().subscribe((res: HttpResponse<ISession[]>) => (this.sessions = res.body || []));
    });
  }

  updateForm(proctoringInstance: IProctoringInstance): void {
    this.editForm.patchValue({
      id: proctoringInstance.id,
      proctorstarttime: proctoringInstance.proctorstarttime,
      proctorendtime: proctoringInstance.proctorendtime,
      proctorid: proctoringInstance.proctorid,
      sessionid: proctoringInstance.sessionid,
      sessionnotes: proctoringInstance.sessionnotes,
      proctorchat: proctoringInstance.proctorchat,
      suspended: proctoringInstance.suspended,
      terminated: proctoringInstance.terminated,
      numberofbreaks: proctoringInstance.numberofbreaks,
      proctorid: proctoringInstance.proctorid,
      sessionid: proctoringInstance.sessionid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const proctoringInstance = this.createFromForm();
    if (proctoringInstance.id !== undefined) {
      this.subscribeToSaveResponse(this.proctoringInstanceService.update(proctoringInstance));
    } else {
      this.subscribeToSaveResponse(this.proctoringInstanceService.create(proctoringInstance));
    }
  }

  private createFromForm(): IProctoringInstance {
    return {
      ...new ProctoringInstance(),
      id: this.editForm.get(['id'])!.value,
      proctorstarttime: this.editForm.get(['proctorstarttime'])!.value,
      proctorendtime: this.editForm.get(['proctorendtime'])!.value,
      proctorid: this.editForm.get(['proctorid'])!.value,
      sessionid: this.editForm.get(['sessionid'])!.value,
      sessionnotes: this.editForm.get(['sessionnotes'])!.value,
      proctorchat: this.editForm.get(['proctorchat'])!.value,
      suspended: this.editForm.get(['suspended'])!.value,
      terminated: this.editForm.get(['terminated'])!.value,
      numberofbreaks: this.editForm.get(['numberofbreaks'])!.value,
      proctorid: this.editForm.get(['proctorid'])!.value,
      sessionid: this.editForm.get(['sessionid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProctoringInstance>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
