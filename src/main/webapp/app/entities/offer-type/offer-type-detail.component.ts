import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferType } from 'app/shared/model/offer-type.model';

@Component({
  selector: 'jhi-offer-type-detail',
  templateUrl: './offer-type-detail.component.html'
})
export class OfferTypeDetailComponent implements OnInit {
  offerType: IOfferType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ offerType }) => (this.offerType = offerType));
  }

  previousState(): void {
    window.history.back();
  }
}
