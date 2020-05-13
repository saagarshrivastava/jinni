import { Moment } from 'moment';
import { IProctor } from 'app/shared/model/proctor.model';
import { ISession } from 'app/shared/model/session.model';

export interface IProctoringInstance {
  id?: number;
  proctorstarttime?: Moment;
  proctorendtime?: Moment;
  proctorid?: number;
  sessionid?: number;
  sessionnotes?: string;
  proctorchat?: string;
  suspended?: boolean;
  terminated?: boolean;
  numberofbreaks?: number;
  proctorid?: IProctor;
  sessionid?: ISession;
}

export class ProctoringInstance implements IProctoringInstance {
  constructor(
    public id?: number,
    public proctorstarttime?: Moment,
    public proctorendtime?: Moment,
    public proctorid?: number,
    public sessionid?: number,
    public sessionnotes?: string,
    public proctorchat?: string,
    public suspended?: boolean,
    public terminated?: boolean,
    public numberofbreaks?: number,
    public proctorid?: IProctor,
    public sessionid?: ISession
  ) {
    this.suspended = this.suspended || false;
    this.terminated = this.terminated || false;
  }
}
