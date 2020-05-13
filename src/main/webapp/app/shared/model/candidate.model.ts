export interface ICandidate {
  id?: number;
  name?: string;
  email?: string;
  certid?: string;
  rhid?: string;
}

export class Candidate implements ICandidate {
  constructor(public id?: number, public name?: string, public email?: string, public certid?: string, public rhid?: string) {}
}
