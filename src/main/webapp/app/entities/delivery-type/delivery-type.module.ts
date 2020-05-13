import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { DeliveryTypeComponent } from './delivery-type.component';
import { DeliveryTypeDetailComponent } from './delivery-type-detail.component';
import { DeliveryTypeUpdateComponent } from './delivery-type-update.component';
import { DeliveryTypeDeleteDialogComponent } from './delivery-type-delete-dialog.component';
import { deliveryTypeRoute } from './delivery-type.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(deliveryTypeRoute)],
  declarations: [DeliveryTypeComponent, DeliveryTypeDetailComponent, DeliveryTypeUpdateComponent, DeliveryTypeDeleteDialogComponent],
  entryComponents: [DeliveryTypeDeleteDialogComponent]
})
export class JinniDeliveryTypeModule {}
