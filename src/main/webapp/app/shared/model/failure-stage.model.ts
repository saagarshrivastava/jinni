export interface IFailureStage {
  id?: number;
  code?: string;
  description?: string;
}

export class FailureStage implements IFailureStage {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
