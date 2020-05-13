import { Moment } from 'moment';
import { IMajorIncidentSource } from 'app/shared/model/major-incident-source.model';

export interface IMajorIncident {
  id?: number;
  majorincidentsourceid?: number;
  starttime?: Moment;
  endtime?: Moment;
  date?: Moment;
  details?: string;
  majorincidentsourceid?: IMajorIncidentSource;
}

export class MajorIncident implements IMajorIncident {
  constructor(
    public id?: number,
    public majorincidentsourceid?: number,
    public starttime?: Moment,
    public endtime?: Moment,
    public date?: Moment,
    public details?: string,
    public majorincidentsourceid?: IMajorIncidentSource
  ) {}
}
