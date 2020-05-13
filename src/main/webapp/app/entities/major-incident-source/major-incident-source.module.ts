import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { MajorIncidentSourceComponent } from './major-incident-source.component';
import { MajorIncidentSourceDetailComponent } from './major-incident-source-detail.component';
import { MajorIncidentSourceUpdateComponent } from './major-incident-source-update.component';
import { MajorIncidentSourceDeleteDialogComponent } from './major-incident-source-delete-dialog.component';
import { majorIncidentSourceRoute } from './major-incident-source.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(majorIncidentSourceRoute)],
  declarations: [
    MajorIncidentSourceComponent,
    MajorIncidentSourceDetailComponent,
    MajorIncidentSourceUpdateComponent,
    MajorIncidentSourceDeleteDialogComponent
  ],
  entryComponents: [MajorIncidentSourceDeleteDialogComponent]
})
export class JinniMajorIncidentSourceModule {}
