import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISchedule, Schedule } from 'app/shared/model/schedule.model';
import { ScheduleService } from './schedule.service';

@Component({
  selector: 'jhi-schedule-update',
  templateUrl: './schedule-update.component.html'
})
export class ScheduleUpdateComponent implements OnInit {
  isSaving = false;
  scheduledsetupstarttimeDp: any;
  actualsetupstarttimeDp: any;
  scheduledsetupendtimeDp: any;
  actualsetupendtimeDp: any;
  scheduledcandidatearrivaltimeDp: any;
  actualcandidatearrivaltimeDp: any;
  scheduledproctorarrivaltimeDp: any;
  actualproctorarrivaltimeDp: any;
  scheduledonboardingstarttimeDp: any;
  actualonboardingstarttimeDp: any;
  scheduledonboardingendtimeDp: any;
  actualonboardingendtimeDp: any;
  scheduledexamstarttimeDp: any;
  actualexamstarttimeDp: any;
  scheduledexamendtimeDp: any;
  actualexamendtimeDp: any;

  editForm = this.fb.group({
    id: [],
    scheduledsetupstarttime: [],
    actualsetupstarttime: [],
    scheduledsetupendtime: [],
    actualsetupendtime: [],
    scheduledcandidatearrivaltime: [],
    actualcandidatearrivaltime: [],
    scheduledproctorarrivaltime: [],
    actualproctorarrivaltime: [],
    scheduledonboardingstarttime: [],
    actualonboardingstarttime: [],
    scheduledonboardingendtime: [],
    actualonboardingendtime: [],
    scheduledexamstarttime: [],
    actualexamstarttime: [],
    scheduledexamendtime: [],
    actualexamendtime: []
  });

  constructor(protected scheduleService: ScheduleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schedule }) => {
      this.updateForm(schedule);
    });
  }

  updateForm(schedule: ISchedule): void {
    this.editForm.patchValue({
      id: schedule.id,
      scheduledsetupstarttime: schedule.scheduledsetupstarttime,
      actualsetupstarttime: schedule.actualsetupstarttime,
      scheduledsetupendtime: schedule.scheduledsetupendtime,
      actualsetupendtime: schedule.actualsetupendtime,
      scheduledcandidatearrivaltime: schedule.scheduledcandidatearrivaltime,
      actualcandidatearrivaltime: schedule.actualcandidatearrivaltime,
      scheduledproctorarrivaltime: schedule.scheduledproctorarrivaltime,
      actualproctorarrivaltime: schedule.actualproctorarrivaltime,
      scheduledonboardingstarttime: schedule.scheduledonboardingstarttime,
      actualonboardingstarttime: schedule.actualonboardingstarttime,
      scheduledonboardingendtime: schedule.scheduledonboardingendtime,
      actualonboardingendtime: schedule.actualonboardingendtime,
      scheduledexamstarttime: schedule.scheduledexamstarttime,
      actualexamstarttime: schedule.actualexamstarttime,
      scheduledexamendtime: schedule.scheduledexamendtime,
      actualexamendtime: schedule.actualexamendtime
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schedule = this.createFromForm();
    if (schedule.id !== undefined) {
      this.subscribeToSaveResponse(this.scheduleService.update(schedule));
    } else {
      this.subscribeToSaveResponse(this.scheduleService.create(schedule));
    }
  }

  private createFromForm(): ISchedule {
    return {
      ...new Schedule(),
      id: this.editForm.get(['id'])!.value,
      scheduledsetupstarttime: this.editForm.get(['scheduledsetupstarttime'])!.value,
      actualsetupstarttime: this.editForm.get(['actualsetupstarttime'])!.value,
      scheduledsetupendtime: this.editForm.get(['scheduledsetupendtime'])!.value,
      actualsetupendtime: this.editForm.get(['actualsetupendtime'])!.value,
      scheduledcandidatearrivaltime: this.editForm.get(['scheduledcandidatearrivaltime'])!.value,
      actualcandidatearrivaltime: this.editForm.get(['actualcandidatearrivaltime'])!.value,
      scheduledproctorarrivaltime: this.editForm.get(['scheduledproctorarrivaltime'])!.value,
      actualproctorarrivaltime: this.editForm.get(['actualproctorarrivaltime'])!.value,
      scheduledonboardingstarttime: this.editForm.get(['scheduledonboardingstarttime'])!.value,
      actualonboardingstarttime: this.editForm.get(['actualonboardingstarttime'])!.value,
      scheduledonboardingendtime: this.editForm.get(['scheduledonboardingendtime'])!.value,
      actualonboardingendtime: this.editForm.get(['actualonboardingendtime'])!.value,
      scheduledexamstarttime: this.editForm.get(['scheduledexamstarttime'])!.value,
      actualexamstarttime: this.editForm.get(['actualexamstarttime'])!.value,
      scheduledexamendtime: this.editForm.get(['scheduledexamendtime'])!.value,
      actualexamendtime: this.editForm.get(['actualexamendtime'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchedule>>): void {
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
