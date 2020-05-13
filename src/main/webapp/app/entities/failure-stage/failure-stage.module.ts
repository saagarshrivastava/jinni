import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { FailureStageComponent } from './failure-stage.component';
import { FailureStageDetailComponent } from './failure-stage-detail.component';
import { FailureStageUpdateComponent } from './failure-stage-update.component';
import { FailureStageDeleteDialogComponent } from './failure-stage-delete-dialog.component';
import { failureStageRoute } from './failure-stage.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(failureStageRoute)],
  declarations: [FailureStageComponent, FailureStageDetailComponent, FailureStageUpdateComponent, FailureStageDeleteDialogComponent],
  entryComponents: [FailureStageDeleteDialogComponent]
})
export class JinniFailureStageModule {}
