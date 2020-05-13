export interface IDeliveryType {
  id?: number;
  code?: string;
  description?: string;
}

export class DeliveryType implements IDeliveryType {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
