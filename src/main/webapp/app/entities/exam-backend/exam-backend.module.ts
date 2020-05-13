import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { ExamBackendComponent } from './exam-backend.component';
import { ExamBackendDetailComponent } from './exam-backend-detail.component';
import { ExamBackendUpdateComponent } from './exam-backend-update.component';
import { ExamBackendDeleteDialogComponent } from './exam-backend-delete-dialog.component';
import { examBackendRoute } from './exam-backend.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(examBackendRoute)],
  declarations: [ExamBackendComponent, ExamBackendDetailComponent, ExamBackendUpdateComponent, ExamBackendDeleteDialogComponent],
  entryComponents: [ExamBackendDeleteDialogComponent]
})
export class JinniExamBackendModule {}
