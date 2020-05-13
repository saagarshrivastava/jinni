import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { ExamTypeComponent } from './exam-type.component';
import { ExamTypeDetailComponent } from './exam-type-detail.component';
import { ExamTypeUpdateComponent } from './exam-type-update.component';
import { ExamTypeDeleteDialogComponent } from './exam-type-delete-dialog.component';
import { examTypeRoute } from './exam-type.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(examTypeRoute)],
  declarations: [ExamTypeComponent, ExamTypeDetailComponent, ExamTypeUpdateComponent, ExamTypeDeleteDialogComponent],
  entryComponents: [ExamTypeDeleteDialogComponent]
})
export class JinniExamTypeModule {}
