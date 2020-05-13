import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ISession, Session } from 'app/shared/model/session.model';
import { SessionService } from './session.service';
import { ISchedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from 'app/entities/schedule/schedule.service';
import { ICandidate } from 'app/shared/model/candidate.model';
import { CandidateService } from 'app/entities/candidate/candidate.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IExamType } from 'app/shared/model/exam-type.model';
import { ExamTypeService } from 'app/entities/exam-type/exam-type.service';
import { IDeliveryType } from 'app/shared/model/delivery-type.model';
import { DeliveryTypeService } from 'app/entities/delivery-type/delivery-type.service';
import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';
import { DeliveryStatusService } from 'app/entities/delivery-status/delivery-status.service';
import { IExam } from 'app/shared/model/exam.model';
import { ExamService } from 'app/entities/exam/exam.service';
import { IExamBackend } from 'app/shared/model/exam-backend.model';
import { ExamBackendService } from 'app/entities/exam-backend/exam-backend.service';

type SelectableEntity = ISchedule | ICandidate | ILocation | IExamType | IDeliveryType | IDeliveryStatus | IExam | IExamBackend;

@Component({
  selector: 'jhi-session-update',
  templateUrl: './session-update.component.html'
})
export class SessionUpdateComponent implements OnInit {
  isSaving = false;
  scheduleids: ISchedule[] = [];
  candidateids: ICandidate[] = [];
  locationids: ILocation[] = [];
  examtypeids: IExamType[] = [];
  deliverytypeids: IDeliveryType[] = [];
  deliverystatusids: IDeliveryStatus[] = [];
  examids: IExam[] = [];
  exambackendids: IExamBackend[] = [];

  editForm = this.fb.group({
    id: [],
    scheduleid: [],
    candidateid: [],
    locationid: [],
    examtypeid: [],
    deliverytypeid: [],
    deliverystatusid: [],
    examid: [],
    exambackendid: [],
    reservationid: [],
    scheduleid: [],
    candidateid: [],
    locationid: [],
    examtypeid: [],
    deliverytypeid: [],
    deliverystatusid: [],
    examid: [],
    exambackendid: []
  });

  constructor(
    protected sessionService: SessionService,
    protected scheduleService: ScheduleService,
    protected candidateService: CandidateService,
    protected locationService: LocationService,
    protected examTypeService: ExamTypeService,
    protected deliveryTypeService: DeliveryTypeService,
    protected deliveryStatusService: DeliveryStatusService,
    protected examService: ExamService,
    protected examBackendService: ExamBackendService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ session }) => {
      this.updateForm(session);

      this.scheduleService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<ISchedule[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISchedule[]) => {
          if (!session.scheduleid || !session.scheduleid.id) {
            this.scheduleids = resBody;
          } else {
            this.scheduleService
              .find(session.scheduleid.id)
              .pipe(
                map((subRes: HttpResponse<ISchedule>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISchedule[]) => (this.scheduleids = concatRes));
          }
        });

      this.candidateService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<ICandidate[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ICandidate[]) => {
          if (!session.candidateid || !session.candidateid.id) {
            this.candidateids = resBody;
          } else {
            this.candidateService
              .find(session.candidateid.id)
              .pipe(
                map((subRes: HttpResponse<ICandidate>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICandidate[]) => (this.candidateids = concatRes));
          }
        });

      this.locationService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!session.locationid || !session.locationid.id) {
            this.locationids = resBody;
          } else {
            this.locationService
              .find(session.locationid.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => (this.locationids = concatRes));
          }
        });

      this.examTypeService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<IExamType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExamType[]) => {
          if (!session.examtypeid || !session.examtypeid.id) {
            this.examtypeids = resBody;
          } else {
            this.examTypeService
              .find(session.examtypeid.id)
              .pipe(
                map((subRes: HttpResponse<IExamType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExamType[]) => (this.examtypeids = concatRes));
          }
        });

      this.deliveryTypeService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<IDeliveryType[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDeliveryType[]) => {
          if (!session.deliverytypeid || !session.deliverytypeid.id) {
            this.deliverytypeids = resBody;
          } else {
            this.deliveryTypeService
              .find(session.deliverytypeid.id)
              .pipe(
                map((subRes: HttpResponse<IDeliveryType>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDeliveryType[]) => (this.deliverytypeids = concatRes));
          }
        });

      this.deliveryStatusService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<IDeliveryStatus[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IDeliveryStatus[]) => {
          if (!session.deliverystatusid || !session.deliverystatusid.id) {
            this.deliverystatusids = resBody;
          } else {
            this.deliveryStatusService
              .find(session.deliverystatusid.id)
              .pipe(
                map((subRes: HttpResponse<IDeliveryStatus>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IDeliveryStatus[]) => (this.deliverystatusids = concatRes));
          }
        });

      this.examService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<IExam[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExam[]) => {
          if (!session.examid || !session.examid.id) {
            this.examids = resBody;
          } else {
            this.examService
              .find(session.examid.id)
              .pipe(
                map((subRes: HttpResponse<IExam>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExam[]) => (this.examids = concatRes));
          }
        });

      this.examBackendService
        .query({ filter: 'session-is-null' })
        .pipe(
          map((res: HttpResponse<IExamBackend[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IExamBackend[]) => {
          if (!session.exambackendid || !session.exambackendid.id) {
            this.exambackendids = resBody;
          } else {
            this.examBackendService
              .find(session.exambackendid.id)
              .pipe(
                map((subRes: HttpResponse<IExamBackend>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IExamBackend[]) => (this.exambackendids = concatRes));
          }
        });
    });
  }

  updateForm(session: ISession): void {
    this.editForm.patchValue({
      id: session.id,
      scheduleid: session.scheduleid,
      candidateid: session.candidateid,
      locationid: session.locationid,
      examtypeid: session.examtypeid,
      deliverytypeid: session.deliverytypeid,
      deliverystatusid: session.deliverystatusid,
      examid: session.examid,
      exambackendid: session.exambackendid,
      reservationid: session.reservationid,
      scheduleid: session.scheduleid,
      candidateid: session.candidateid,
      locationid: session.locationid,
      examtypeid: session.examtypeid,
      deliverytypeid: session.deliverytypeid,
      deliverystatusid: session.deliverystatusid,
      examid: session.examid,
      exambackendid: session.exambackendid
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const session = this.createFromForm();
    if (session.id !== undefined) {
      this.subscribeToSaveResponse(this.sessionService.update(session));
    } else {
      this.subscribeToSaveResponse(this.sessionService.create(session));
    }
  }

  private createFromForm(): ISession {
    return {
      ...new Session(),
      id: this.editForm.get(['id'])!.value,
      scheduleid: this.editForm.get(['scheduleid'])!.value,
      candidateid: this.editForm.get(['candidateid'])!.value,
      locationid: this.editForm.get(['locationid'])!.value,
      examtypeid: this.editForm.get(['examtypeid'])!.value,
      deliverytypeid: this.editForm.get(['deliverytypeid'])!.value,
      deliverystatusid: this.editForm.get(['deliverystatusid'])!.value,
      examid: this.editForm.get(['examid'])!.value,
      exambackendid: this.editForm.get(['exambackendid'])!.value,
      reservationid: this.editForm.get(['reservationid'])!.value,
      scheduleid: this.editForm.get(['scheduleid'])!.value,
      candidateid: this.editForm.get(['candidateid'])!.value,
      locationid: this.editForm.get(['locationid'])!.value,
      examtypeid: this.editForm.get(['examtypeid'])!.value,
      deliverytypeid: this.editForm.get(['deliverytypeid'])!.value,
      deliverystatusid: this.editForm.get(['deliverystatusid'])!.value,
      examid: this.editForm.get(['examid'])!.value,
      exambackendid: this.editForm.get(['exambackendid'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISession>>): void {
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
