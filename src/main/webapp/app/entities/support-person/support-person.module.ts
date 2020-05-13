import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { SupportPersonComponent } from './support-person.component';
import { SupportPersonDetailComponent } from './support-person-detail.component';
import { SupportPersonUpdateComponent } from './support-person-update.component';
import { SupportPersonDeleteDialogComponent } from './support-person-delete-dialog.component';
import { supportPersonRoute } from './support-person.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(supportPersonRoute)],
  declarations: [SupportPersonComponent, SupportPersonDetailComponent, SupportPersonUpdateComponent, SupportPersonDeleteDialogComponent],
  entryComponents: [SupportPersonDeleteDialogComponent]
})
export class JinniSupportPersonModule {}
