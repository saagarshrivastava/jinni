import { ISession } from 'app/shared/model/session.model';
import { IFailureStage } from 'app/shared/model/failure-stage.model';
import { IMajorIncident } from 'app/shared/model/major-incident.model';

export interface IIncident {
  id?: number;
  sessionid?: number;
  majorincidentid?: number;
  failurestageid?: number;
  summary?: string;
  investigationreport?: string;
  servicenowticketid?: string;
  sessionid?: ISession;
  failurestageid?: IFailureStage;
  majorincidentid?: IMajorIncident;
}

export class Incident implements IIncident {
  constructor(
    public id?: number,
    public sessionid?: number,
    public majorincidentid?: number,
    public failurestageid?: number,
    public summary?: string,
    public investigationreport?: string,
    public servicenowticketid?: string,
    public sessionid?: ISession,
    public failurestageid?: IFailureStage,
    public majorincidentid?: IMajorIncident
  ) {}
}
