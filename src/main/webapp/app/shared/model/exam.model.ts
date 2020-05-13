export interface IExam {
  id?: number;
  code?: string;
  description?: string;
  version?: string;
}

export class Exam implements IExam {
  constructor(public id?: number, public code?: string, public description?: string, public version?: string) {}
}
