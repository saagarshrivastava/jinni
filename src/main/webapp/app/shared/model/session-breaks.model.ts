import { Moment } from 'moment';
import { IProctoringInstance } from 'app/shared/model/proctoring-instance.model';

export interface ISessionBreaks {
  id?: number;
  starttime?: Moment;
  endtime?: Moment;
  proctoringinstanceid?: number;
  proctoringinstanceid?: IProctoringInstance;
}

export class SessionBreaks implements ISessionBreaks {
  constructor(
    public id?: number,
    public starttime?: Moment,
    public endtime?: Moment,
    public proctoringinstanceid?: number,
    public proctoringinstanceid?: IProctoringInstance
  ) {}
}
