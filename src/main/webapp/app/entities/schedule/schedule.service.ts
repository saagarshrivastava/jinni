import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISchedule } from 'app/shared/model/schedule.model';

type EntityResponseType = HttpResponse<ISchedule>;
type EntityArrayResponseType = HttpResponse<ISchedule[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleService {
  public resourceUrl = SERVER_API_URL + 'api/schedules';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/schedules';

  constructor(protected http: HttpClient) {}

  create(schedule: ISchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schedule);
    return this.http
      .post<ISchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(schedule: ISchedule): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(schedule);
    return this.http
      .put<ISchedule>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISchedule>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISchedule[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISchedule[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(schedule: ISchedule): ISchedule {
    const copy: ISchedule = Object.assign({}, schedule, {
      scheduledsetupstarttime:
        schedule.scheduledsetupstarttime && schedule.scheduledsetupstarttime.isValid()
          ? schedule.scheduledsetupstarttime.format(DATE_FORMAT)
          : undefined,
      actualsetupstarttime:
        schedule.actualsetupstarttime && schedule.actualsetupstarttime.isValid()
          ? schedule.actualsetupstarttime.format(DATE_FORMAT)
          : undefined,
      scheduledsetupendtime:
        schedule.scheduledsetupendtime && schedule.scheduledsetupendtime.isValid()
          ? schedule.scheduledsetupendtime.format(DATE_FORMAT)
          : undefined,
      actualsetupendtime:
        schedule.actualsetupendtime && schedule.actualsetupendtime.isValid() ? schedule.actualsetupendtime.format(DATE_FORMAT) : undefined,
      scheduledcandidatearrivaltime:
        schedule.scheduledcandidatearrivaltime && schedule.scheduledcandidatearrivaltime.isValid()
          ? schedule.scheduledcandidatearrivaltime.format(DATE_FORMAT)
          : undefined,
      actualcandidatearrivaltime:
        schedule.actualcandidatearrivaltime && schedule.actualcandidatearrivaltime.isValid()
          ? schedule.actualcandidatearrivaltime.format(DATE_FORMAT)
          : undefined,
      scheduledproctorarrivaltime:
        schedule.scheduledproctorarrivaltime && schedule.scheduledproctorarrivaltime.isValid()
          ? schedule.scheduledproctorarrivaltime.format(DATE_FORMAT)
          : undefined,
      actualproctorarrivaltime:
        schedule.actualproctorarrivaltime && schedule.actualproctorarrivaltime.isValid()
          ? schedule.actualproctorarrivaltime.format(DATE_FORMAT)
          : undefined,
      scheduledonboardingstarttime:
        schedule.scheduledonboardingstarttime && schedule.scheduledonboardingstarttime.isValid()
          ? schedule.scheduledonboardingstarttime.format(DATE_FORMAT)
          : undefined,
      actualonboardingstarttime:
        schedule.actualonboardingstarttime && schedule.actualonboardingstarttime.isValid()
          ? schedule.actualonboardingstarttime.format(DATE_FORMAT)
          : undefined,
      scheduledonboardingendtime:
        schedule.scheduledonboardingendtime && schedule.scheduledonboardingendtime.isValid()
          ? schedule.scheduledonboardingendtime.format(DATE_FORMAT)
          : undefined,
      actualonboardingendtime:
        schedule.actualonboardingendtime && schedule.actualonboardingendtime.isValid()
          ? schedule.actualonboardingendtime.format(DATE_FORMAT)
          : undefined,
      scheduledexamstarttime:
        schedule.scheduledexamstarttime && schedule.scheduledexamstarttime.isValid()
          ? schedule.scheduledexamstarttime.format(DATE_FORMAT)
          : undefined,
      actualexamstarttime:
        schedule.actualexamstarttime && schedule.actualexamstarttime.isValid()
          ? schedule.actualexamstarttime.format(DATE_FORMAT)
          : undefined,
      scheduledexamendtime:
        schedule.scheduledexamendtime && schedule.scheduledexamendtime.isValid()
          ? schedule.scheduledexamendtime.format(DATE_FORMAT)
          : undefined,
      actualexamendtime:
        schedule.actualexamendtime && schedule.actualexamendtime.isValid() ? schedule.actualexamendtime.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.scheduledsetupstarttime = res.body.scheduledsetupstarttime ? moment(res.body.scheduledsetupstarttime) : undefined;
      res.body.actualsetupstarttime = res.body.actualsetupstarttime ? moment(res.body.actualsetupstarttime) : undefined;
      res.body.scheduledsetupendtime = res.body.scheduledsetupendtime ? moment(res.body.scheduledsetupendtime) : undefined;
      res.body.actualsetupendtime = res.body.actualsetupendtime ? moment(res.body.actualsetupendtime) : undefined;
      res.body.scheduledcandidatearrivaltime = res.body.scheduledcandidatearrivaltime
        ? moment(res.body.scheduledcandidatearrivaltime)
        : undefined;
      res.body.actualcandidatearrivaltime = res.body.actualcandidatearrivaltime ? moment(res.body.actualcandidatearrivaltime) : undefined;
      res.body.scheduledproctorarrivaltime = res.body.scheduledproctorarrivaltime
        ? moment(res.body.scheduledproctorarrivaltime)
        : undefined;
      res.body.actualproctorarrivaltime = res.body.actualproctorarrivaltime ? moment(res.body.actualproctorarrivaltime) : undefined;
      res.body.scheduledonboardingstarttime = res.body.scheduledonboardingstarttime
        ? moment(res.body.scheduledonboardingstarttime)
        : undefined;
      res.body.actualonboardingstarttime = res.body.actualonboardingstarttime ? moment(res.body.actualonboardingstarttime) : undefined;
      res.body.scheduledonboardingendtime = res.body.scheduledonboardingendtime ? moment(res.body.scheduledonboardingendtime) : undefined;
      res.body.actualonboardingendtime = res.body.actualonboardingendtime ? moment(res.body.actualonboardingendtime) : undefined;
      res.body.scheduledexamstarttime = res.body.scheduledexamstarttime ? moment(res.body.scheduledexamstarttime) : undefined;
      res.body.actualexamstarttime = res.body.actualexamstarttime ? moment(res.body.actualexamstarttime) : undefined;
      res.body.scheduledexamendtime = res.body.scheduledexamendtime ? moment(res.body.scheduledexamendtime) : undefined;
      res.body.actualexamendtime = res.body.actualexamendtime ? moment(res.body.actualexamendtime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((schedule: ISchedule) => {
        schedule.scheduledsetupstarttime = schedule.scheduledsetupstarttime ? moment(schedule.scheduledsetupstarttime) : undefined;
        schedule.actualsetupstarttime = schedule.actualsetupstarttime ? moment(schedule.actualsetupstarttime) : undefined;
        schedule.scheduledsetupendtime = schedule.scheduledsetupendtime ? moment(schedule.scheduledsetupendtime) : undefined;
        schedule.actualsetupendtime = schedule.actualsetupendtime ? moment(schedule.actualsetupendtime) : undefined;
        schedule.scheduledcandidatearrivaltime = schedule.scheduledcandidatearrivaltime
          ? moment(schedule.scheduledcandidatearrivaltime)
          : undefined;
        schedule.actualcandidatearrivaltime = schedule.actualcandidatearrivaltime ? moment(schedule.actualcandidatearrivaltime) : undefined;
        schedule.scheduledproctorarrivaltime = schedule.scheduledproctorarrivaltime
          ? moment(schedule.scheduledproctorarrivaltime)
          : undefined;
        schedule.actualproctorarrivaltime = schedule.actualproctorarrivaltime ? moment(schedule.actualproctorarrivaltime) : undefined;
        schedule.scheduledonboardingstarttime = schedule.scheduledonboardingstarttime
          ? moment(schedule.scheduledonboardingstarttime)
          : undefined;
        schedule.actualonboardingstarttime = schedule.actualonboardingstarttime ? moment(schedule.actualonboardingstarttime) : undefined;
        schedule.scheduledonboardingendtime = schedule.scheduledonboardingendtime ? moment(schedule.scheduledonboardingendtime) : undefined;
        schedule.actualonboardingendtime = schedule.actualonboardingendtime ? moment(schedule.actualonboardingendtime) : undefined;
        schedule.scheduledexamstarttime = schedule.scheduledexamstarttime ? moment(schedule.scheduledexamstarttime) : undefined;
        schedule.actualexamstarttime = schedule.actualexamstarttime ? moment(schedule.actualexamstarttime) : undefined;
        schedule.scheduledexamendtime = schedule.scheduledexamendtime ? moment(schedule.scheduledexamendtime) : undefined;
        schedule.actualexamendtime = schedule.actualexamendtime ? moment(schedule.actualexamendtime) : undefined;
      });
    }
    return res;
  }
}
