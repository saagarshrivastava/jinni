import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IMajorIncident, MajorIncident } from 'app/shared/model/major-incident.model';
import { MajorIncidentService } from './major-incident.service';
import { IMajorIncidentSource } from 'app/shared/model/major-incident-source.model';
import { MajorIncidentSourceService } from 'app/entities/major-incident-source/major-incident-source.service';

@Component({
  selector: 'jhi-major-incident-update',
  templateUrl: './major-incident-update.component.html'
})
export class MajorIncidentUpdateComponent implements OnInit {
  isSaving = false;
  majorincidentsourceids: IMajorIncidentSource[] = [];
  starttimeDp: any;
  endtimeDp: any;
  dateDp: any;

  editForm = this.fb.group({
    id: [],
    majorincidentsourceid: [],
    starttime: [],
    endtime: [],
    date: [],
    details: [],
    majorincidentsourceid: []
  });

  constructor(
    protected majorIncidentService: MajorIncidentService,
    protected majorIncidentSourceService: MajorIncidentSourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ majorIncident }) => {
      this.updateForm(majorIncident);

      this.majorIncidentSourceService
        .query({ filter: 'majorincident-is-null' })
        .pipe(
          map((res: HttpResponse<IMajorIncidentSource[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMajorIncidentSource[]) => {
          if (!majorIncident.majorincidentsourceid || !majorIncident.majorincidentsourceid.id) {
            this.majorincidentsourceids = resBody;
          } else {
            this.majorIncidentSourceService
              .find(majorIncident.majorincidentsourceid.id)
              .pipe(
                map((subRes: HttpResponse<IMajorIncidentSource>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMajorIncidentSource[]) => (this.majorincidentsourceids = concatRes));
          }
        });
    });
  }

  updateForm(majorIncident: IMajorIncident): void {
    this.editForm.patchValue({
      id: majorIncident.id,
      majorincidentsourceid: majorIncident.majorincidentsourceid,
      starttime: majorIncident.starttime,
      endtime: majorIncident.endtime,
      date: majorIncident.date,
      details: majorIncident.details,
      majorincidentsourceid: majorIncident.majorincidentsourceid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const majorIncident = this.createFromForm();
    if (majorIncident.id !== undefined) {
      this.subscribeToSaveResponse(this.majorIncidentService.update(majorIncident));
    } else {
      this.subscribeToSaveResponse(this.majorIncidentService.create(majorIncident));
    }
  }

  private createFromForm(): IMajorIncident {
    return {
      ...new MajorIncident(),
      id: this.editForm.get(['id'])!.value,
      majorincidentsourceid: this.editForm.get(['majorincidentsourceid'])!.value,
      starttime: this.editForm.get(['starttime'])!.value,
      endtime: this.editForm.get(['endtime'])!.value,
      date: this.editForm.get(['date'])!.value,
      details: this.editForm.get(['details'])!.value,
      majorincidentsourceid: this.editForm.get(['majorincidentsourceid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMajorIncident>>): void {
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

  trackById(index: number, item: IMajorIncidentSource): any {
    return item.id;
  }
}
