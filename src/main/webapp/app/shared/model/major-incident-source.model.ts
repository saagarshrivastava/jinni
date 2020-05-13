export interface IMajorIncidentSource {
  id?: number;
  code?: string;
  description?: string;
}

export class MajorIncidentSource implements IMajorIncidentSource {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
