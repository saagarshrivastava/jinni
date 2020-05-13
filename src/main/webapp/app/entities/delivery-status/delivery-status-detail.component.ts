import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';

@Component({
  selector: 'jhi-delivery-status-detail',
  templateUrl: './delivery-status-detail.component.html'
})
export class DeliveryStatusDetailComponent implements OnInit {
  deliveryStatus: IDeliveryStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ deliveryStatus }) => (this.deliveryStatus = deliveryStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
