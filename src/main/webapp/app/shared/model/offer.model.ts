import { IOfferType } from 'app/shared/model/offer-type.model';
import { IIncident } from 'app/shared/model/incident.model';
import { IExam } from 'app/shared/model/exam.model';

export interface IOffer {
  id?: number;
  incidentid?: number;
  offertypeid?: number;
  examid?: number;
  discountpercentage?: number;
  offertypeid?: IOfferType;
  incidentid?: IIncident;
  examid?: IExam;
}

export class Offer implements IOffer {
  constructor(
    public id?: number,
    public incidentid?: number,
    public offertypeid?: number,
    public examid?: number,
    public discountpercentage?: number,
    public offertypeid?: IOfferType,
    public incidentid?: IIncident,
    public examid?: IExam
  ) {}
}
