import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { OfferTypeComponent } from './offer-type.component';
import { OfferTypeDetailComponent } from './offer-type-detail.component';
import { OfferTypeUpdateComponent } from './offer-type-update.component';
import { OfferTypeDeleteDialogComponent } from './offer-type-delete-dialog.component';
import { offerTypeRoute } from './offer-type.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(offerTypeRoute)],
  declarations: [OfferTypeComponent, OfferTypeDetailComponent, OfferTypeUpdateComponent, OfferTypeDeleteDialogComponent],
  entryComponents: [OfferTypeDeleteDialogComponent]
})
export class JinniOfferTypeModule {}
