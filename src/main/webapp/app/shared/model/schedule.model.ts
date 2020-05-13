import { Moment } from 'moment';

export interface ISchedule {
  id?: number;
  scheduledsetupstarttime?: Moment;
  actualsetupstarttime?: Moment;
  scheduledsetupendtime?: Moment;
  actualsetupendtime?: Moment;
  scheduledcandidatearrivaltime?: Moment;
  actualcandidatearrivaltime?: Moment;
  scheduledproctorarrivaltime?: Moment;
  actualproctorarrivaltime?: Moment;
  scheduledonboardingstarttime?: Moment;
  actualonboardingstarttime?: Moment;
  scheduledonboardingendtime?: Moment;
  actualonboardingendtime?: Moment;
  scheduledexamstarttime?: Moment;
  actualexamstarttime?: Moment;
  scheduledexamendtime?: Moment;
  actualexamendtime?: Moment;
}

export class Schedule implements ISchedule {
  constructor(
    public id?: number,
    public scheduledsetupstarttime?: Moment,
    public actualsetupstarttime?: Moment,
    public scheduledsetupendtime?: Moment,
    public actualsetupendtime?: Moment,
    public scheduledcandidatearrivaltime?: Moment,
    public actualcandidatearrivaltime?: Moment,
    public scheduledproctorarrivaltime?: Moment,
    public actualproctorarrivaltime?: Moment,
    public scheduledonboardingstarttime?: Moment,
    public actualonboardingstarttime?: Moment,
    public scheduledonboardingendtime?: Moment,
    public actualonboardingendtime?: Moment,
    public scheduledexamstarttime?: Moment,
    public actualexamstarttime?: Moment,
    public scheduledexamendtime?: Moment,
    public actualexamendtime?: Moment
  ) {}
}
