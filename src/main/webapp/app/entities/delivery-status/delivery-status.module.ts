import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { DeliveryStatusComponent } from './delivery-status.component';
import { DeliveryStatusDetailComponent } from './delivery-status-detail.component';
import { DeliveryStatusUpdateComponent } from './delivery-status-update.component';
import { DeliveryStatusDeleteDialogComponent } from './delivery-status-delete-dialog.component';
import { deliveryStatusRoute } from './delivery-status.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(deliveryStatusRoute)],
  declarations: [
    DeliveryStatusComponent,
    DeliveryStatusDetailComponent,
    DeliveryStatusUpdateComponent,
    DeliveryStatusDeleteDialogComponent
  ],
  entryComponents: [DeliveryStatusDeleteDialogComponent]
})
export class JinniDeliveryStatusModule {}
