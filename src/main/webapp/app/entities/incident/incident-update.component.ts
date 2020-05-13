import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IIncident, Incident } from 'app/shared/model/incident.model';
import { IncidentService } from './incident.service';
import { ISession } from 'app/shared/model/session.model';
import { SessionService } from 'app/entities/session/session.service';
import { IFailureStage } from 'app/shared/model/failure-stage.model';
import { FailureStageService } from 'app/entities/failure-stage/failure-stage.service';
import { IMajorIncident } from 'app/shared/model/major-incident.model';
import { MajorIncidentService } from 'app/entities/major-incident/major-incident.service';

type SelectableEntity = ISession | IFailureStage | IMajorIncident;

@Component({
  selector: 'jhi-incident-update',
  templateUrl: './incident-update.component.html'
})
export class IncidentUpdateComponent implements OnInit {
  isSaving = false;
  sessionids: ISession[] = [];
  failurestageids: IFailureStage[] = [];
  majorincidents: IMajorIncident[] = [];

  editForm = this.fb.group({
    id: [],
    sessionid: [],
    majorincidentid: [],
    failurestageid: [],
    summary: [],
    investigationreport: [],
    servicenowticketid: [],
    sessionid: [],
    failurestageid: [],
    majorincidentid: []
  });

  constructor(
    protected incidentService: IncidentService,
    protected sessionService: SessionService,
    protected failureStageService: FailureStageService,
    protected majorIncidentService: MajorIncidentService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ incident }) => {
      this.updateForm(incident);

      this.sessionService
        .query({ filter: 'incident-is-null' })
        .pipe(
          map((res: HttpResponse<ISession[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISession[]) => {
          if (!incident.sessionid || !incident.sessionid.id) {
            this.sessionids = resBody;
          } else {
            this.sessionService
              .find(incident.sessionid.id)
              .pipe(
                map((subRes: HttpResponse<ISession>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISession[]) => (this.sessionids = concatRes));
          }
        });

      this.failureStageService
        .query({ filter: 'incident-is-null' })
        .pipe(
          map((res: HttpResponse<IFailureStage[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IFailureStage[]) => {
          if (!incident.failurestageid || !incident.failurestageid.id) {
            this.failurestageids = resBody;
          } else {
            this.failureStageService
              .find(incident.failurestageid.id)
              .pipe(
                map((subRes: HttpResponse<IFailureStage>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IFailureStage[]) => (this.failurestageids = concatRes));
          }
        });

      this.majorIncidentService.query().subscribe((res: HttpResponse<IMajorIncident[]>) => (this.majorincidents = res.body || []));
    });
  }

  updateForm(incident: IIncident): void {
    this.editForm.patchValue({
      id: incident.id,
      sessionid: incident.sessionid,
      majorincidentid: incident.majorincidentid,
      failurestageid: incident.failurestageid,
      summary: incident.summary,
      investigationreport: incident.investigationreport,
      servicenowticketid: incident.servicenowticketid,
      sessionid: incident.sessionid,
      failurestageid: incident.failurestageid,
      majorincidentid: incident.majorincidentid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const incident = this.createFromForm();
    if (incident.id !== undefined) {
      this.subscribeToSaveResponse(this.incidentService.update(incident));
    } else {
      this.subscribeToSaveResponse(this.incidentService.create(incident));
    }
  }

  private createFromForm(): IIncident {
    return {
      ...new Incident(),
      id: this.editForm.get(['id'])!.value,
      sessionid: this.editForm.get(['sessionid'])!.value,
      majorincidentid: this.editForm.get(['majorincidentid'])!.value,
      failurestageid: this.editForm.get(['failurestageid'])!.value,
      summary: this.editForm.get(['summary'])!.value,
      investigationreport: this.editForm.get(['investigationreport'])!.value,
      servicenowticketid: this.editForm.get(['servicenowticketid'])!.value,
      sessionid: this.editForm.get(['sessionid'])!.value,
      failurestageid: this.editForm.get(['failurestageid'])!.value,
      majorincidentid: this.editForm.get(['majorincidentid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncident>>): void {
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
