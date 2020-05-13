import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { ProctoringInstanceComponent } from './proctoring-instance.component';
import { ProctoringInstanceDetailComponent } from './proctoring-instance-detail.component';
import { ProctoringInstanceUpdateComponent } from './proctoring-instance-update.component';
import { ProctoringInstanceDeleteDialogComponent } from './proctoring-instance-delete-dialog.component';
import { proctoringInstanceRoute } from './proctoring-instance.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(proctoringInstanceRoute)],
  declarations: [
    ProctoringInstanceComponent,
    ProctoringInstanceDetailComponent,
    ProctoringInstanceUpdateComponent,
    ProctoringInstanceDeleteDialogComponent
  ],
  entryComponents: [ProctoringInstanceDeleteDialogComponent]
})
export class JinniProctoringInstanceModule {}
