export interface IDeliveryStatus {
  id?: number;
  code?: string;
  description?: string;
}

export class DeliveryStatus implements IDeliveryStatus {
  constructor(public id?: number, public code?: string, public description?: string) {}
}
