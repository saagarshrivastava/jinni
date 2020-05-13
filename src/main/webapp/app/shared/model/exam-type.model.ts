export interface IExamType {
  id?: number;
  code?: string;
  description?: string;
}

export class ExamType implements IExamType {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
