import { ICategory } from 'app/shared/model/category.model';
import { IIncident } from 'app/shared/model/incident.model';

export interface ICategoryInstance {
  id?: number;
  incidentid?: number;
  categoryid?: number;
  rank?: number;
  categoryid?: ICategory;
  incidentid?: IIncident;
}

export class CategoryInstance implements ICategoryInstance {
  constructor(
    public id?: number,
    public incidentid?: number,
    public categoryid?: number,
    public rank?: number,
    public categoryid?: ICategory,
    public incidentid?: IIncident
  ) {}
}
