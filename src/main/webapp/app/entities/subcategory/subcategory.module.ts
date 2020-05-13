import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JinniSharedModule } from 'app/shared/shared.module';
import { SubcategoryComponent } from './subcategory.component';
import { SubcategoryDetailComponent } from './subcategory-detail.component';
import { SubcategoryUpdateComponent } from './subcategory-update.component';
import { SubcategoryDeleteDialogComponent } from './subcategory-delete-dialog.component';
import { subcategoryRoute } from './subcategory.route';

@NgModule({
  imports: [JinniSharedModule, RouterModule.forChild(subcategoryRoute)],
  declarations: [SubcategoryComponent, SubcategoryDetailComponent, SubcategoryUpdateComponent, SubcategoryDeleteDialogComponent],
  entryComponents: [SubcategoryDeleteDialogComponent]
})
export class JinniSubcategoryModule {}
