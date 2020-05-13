export interface IOfferType {
  id?: number;
  code?: string;
  description?: string;
}

export class OfferType implements IOfferType {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
