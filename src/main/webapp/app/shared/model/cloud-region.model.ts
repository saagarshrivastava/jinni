export interface ICloudRegion {
  id?: number;
  code?: string;
  description?: string;
}

export class CloudRegion implements ICloudRegion {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
