export interface ISupportPerson {
  id?: number;
  name?: string;
  email?: string;
  office?: string;
}

export class SupportPerson implements ISupportPerson {
  constructor(public id?: number, public name?: string, public email?: string, public office?: string) {}
}
