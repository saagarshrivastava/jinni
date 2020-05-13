import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { SessionBreaksComponent } from './session-breaks.component';
import { SessionBreaksDetailComponent } from './session-breaks-detail.component';
import { SessionBreaksUpdateComponent } from './session-breaks-update.component';
import { SessionBreaksDeleteDialogComponent } from './session-breaks-delete-dialog.component';
import { sessionBreaksRoute } from './session-breaks.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(sessionBreaksRoute)],
  declarations: [SessionBreaksComponent, SessionBreaksDetailComponent, SessionBreaksUpdateComponent, SessionBreaksDeleteDialogComponent],
  entryComponents: [SessionBreaksDeleteDialogComponent]
})
export class JinniSessionBreaksModule {}
