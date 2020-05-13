export interface IExamBackend {
  id?: number;
  code?: string;
  description?: string;
}

export class ExamBackend implements IExamBackend {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
