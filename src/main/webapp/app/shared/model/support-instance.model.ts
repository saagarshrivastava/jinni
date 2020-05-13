import { Moment } from 'moment';
import { ISupportPerson } from 'app/shared/model/support-person.model';
import { ISession } from 'app/shared/model/session.model';

export interface ISupportInstance {
  id?: number;
  starttime?: Moment;
  endtime?: Moment;
  chatlogs?: string;
  sessionid?: number;
  supportpersonid?: number;
  supportpersonid?: ISupportPerson;
  sessionid?: ISession;
}

export class SupportInstance implements ISupportInstance {
  constructor(
    public id?: number,
    public starttime?: Moment,
    public endtime?: Moment,
    public chatlogs?: string,
    public sessionid?: number,
    public supportpersonid?: number,
    public supportpersonid?: ISupportPerson,
    public sessionid?: ISession
  ) {}
}
