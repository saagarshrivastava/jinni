export interface IProctor {
  id?: number;
  name?: string;
  email?: string;
  office?: string;
}

export class Proctor implements IProctor {
  constructor(public id?: number, public name?: string, public email?: string, public office?: string) {}
}
