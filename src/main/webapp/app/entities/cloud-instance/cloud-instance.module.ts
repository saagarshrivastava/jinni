import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { CloudInstanceComponent } from './cloud-instance.component';
import { CloudInstanceDetailComponent } from './cloud-instance-detail.component';
import { CloudInstanceUpdateComponent } from './cloud-instance-update.component';
import { CloudInstanceDeleteDialogComponent } from './cloud-instance-delete-dialog.component';
import { cloudInstanceRoute } from './cloud-instance.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(cloudInstanceRoute)],
  declarations: [CloudInstanceComponent, CloudInstanceDetailComponent, CloudInstanceUpdateComponent, CloudInstanceDeleteDialogComponent],
  entryComponents: [CloudInstanceDeleteDialogComponent]
})
export class JinniCloudInstanceModule {}
