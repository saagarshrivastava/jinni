export interface IRegion {
  id?: number;
  code?: string;
  description?: string;
}

export class Region implements IRegion {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
