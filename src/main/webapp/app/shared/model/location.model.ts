import { IRegion } from 'app/shared/model/region.model';

export interface ILocation {
  id?: number;
  rhieid?: number;
  city?: string;
  country?: string;
  partner?: string;
  regionid?: number;
  regionid?: IRegion;
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public rhieid?: number,
    public city?: string,
    public country?: string,
    public partner?: string,
    public regionid?: number,
    public regionid?: IRegion
  ) {}
}
