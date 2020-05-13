import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { SupportInstanceComponent } from './support-instance.component';
import { SupportInstanceDetailComponent } from './support-instance-detail.component';
import { SupportInstanceUpdateComponent } from './support-instance-update.component';
import { SupportInstanceDeleteDialogComponent } from './support-instance-delete-dialog.component';
import { supportInstanceRoute } from './support-instance.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(supportInstanceRoute)],
  declarations: [
    SupportInstanceComponent,
    SupportInstanceDetailComponent,
    SupportInstanceUpdateComponent,
    SupportInstanceDeleteDialogComponent
  ],
  entryComponents: [SupportInstanceDeleteDialogComponent]
})
export class JinniSupportInstanceModule {}
