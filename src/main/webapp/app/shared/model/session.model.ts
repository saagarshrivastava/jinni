import { ISchedule } from 'app/shared/model/schedule.model';
import { ICandidate } from 'app/shared/model/candidate.model';
import { ILocation } from 'app/shared/model/location.model';
import { IExamType } from 'app/shared/model/exam-type.model';
import { IDeliveryType } from 'app/shared/model/delivery-type.model';
import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';
import { IExam } from 'app/shared/model/exam.model';
import { IExamBackend } from 'app/shared/model/exam-backend.model';

export interface ISession {
  id?: number;
  scheduleid?: number;
  candidateid?: number;
  locationid?: number;
  examtypeid?: number;
  deliverytypeid?: number;
  deliverystatusid?: number;
  examid?: number;
  exambackendid?: number;
  reservationid?: string;
  scheduleid?: ISchedule;
  candidateid?: ICandidate;
  locationid?: ILocation;
  examtypeid?: IExamType;
  deliverytypeid?: IDeliveryType;
  deliverystatusid?: IDeliveryStatus;
  examid?: IExam;
  exambackendid?: IExamBackend;
}

export class Session implements ISession {
  constructor(
    public id?: number,
    public scheduleid?: number,
    public candidateid?: number,
    public locationid?: number,
    public examtypeid?: number,
    public deliverytypeid?: number,
    public deliverystatusid?: number,
    public examid?: number,
    public exambackendid?: number,
    public reservationid?: string,
    public scheduleid?: ISchedule,
    public candidateid?: ICandidate,
    public locationid?: ILocation,
    public examtypeid?: IExamType,
    public deliverytypeid?: IDeliveryType,
    public deliverystatusid?: IDeliveryStatus,
    public examid?: IExam,
    public exambackendid?: IExamBackend
  ) {}
}
