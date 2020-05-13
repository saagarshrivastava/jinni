import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { CloudRegionComponent } from './cloud-region.component';
import { CloudRegionDetailComponent } from './cloud-region-detail.component';
import { CloudRegionUpdateComponent } from './cloud-region-update.component';
import { CloudRegionDeleteDialogComponent } from './cloud-region-delete-dialog.component';
import { cloudRegionRoute } from './cloud-region.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(cloudRegionRoute)],
  declarations: [CloudRegionComponent, CloudRegionDetailComponent, CloudRegionUpdateComponent, CloudRegionDeleteDialogComponent],
  entryComponents: [CloudRegionDeleteDialogComponent]
})
export class JinniCloudRegionModule {}
