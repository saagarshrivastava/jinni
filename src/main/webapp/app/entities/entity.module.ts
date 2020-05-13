import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'session',
        loadChildren: () => import('./session/session.module').then(m => m.JinniSessionModule)
      },
      {
        path: 'schedule',
        loadChildren: () => import('./schedule/schedule.module').then(m => m.JinniScheduleModule)
      },
      {
        path: 'candidate',
        loadChildren: () => import('./candidate/candidate.module').then(m => m.JinniCandidateModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JinniLocationModule)
      },
      {
        path: 'proctoring-instance',
        loadChildren: () => import('./proctoring-instance/proctoring-instance.module').then(m => m.JinniProctoringInstanceModule)
      },
      {
        path: 'session-breaks',
        loadChildren: () => import('./session-breaks/session-breaks.module').then(m => m.JinniSessionBreaksModule)
      },
      {
        path: 'incident',
        loadChildren: () => import('./incident/incident.module').then(m => m.JinniIncidentModule)
      },
      {
        path: 'category-instance',
        loadChildren: () => import('./category-instance/category-instance.module').then(m => m.JinniCategoryInstanceModule)
      },
      {
        path: 'subcategory-instance',
        loadChildren: () => import('./subcategory-instance/subcategory-instance.module').then(m => m.JinniSubcategoryInstanceModule)
      },
      {
        path: 'major-incident',
        loadChildren: () => import('./major-incident/major-incident.module').then(m => m.JinniMajorIncidentModule)
      },
      {
        path: 'major-incident-source',
        loadChildren: () => import('./major-incident-source/major-incident-source.module').then(m => m.JinniMajorIncidentSourceModule)
      },
      {
        path: 'proctor',
        loadChildren: () => import('./proctor/proctor.module').then(m => m.JinniProctorModule)
      },
      {
        path: 'support-instance',
        loadChildren: () => import('./support-instance/support-instance.module').then(m => m.JinniSupportInstanceModule)
      },
      {
        path: 'support-person',
        loadChildren: () => import('./support-person/support-person.module').then(m => m.JinniSupportPersonModule)
      },
      {
        path: 'delivery-type',
        loadChildren: () => import('./delivery-type/delivery-type.module').then(m => m.JinniDeliveryTypeModule)
      },
      {
        path: 'exam-type',
        loadChildren: () => import('./exam-type/exam-type.module').then(m => m.JinniExamTypeModule)
      },
      {
        path: 'delivery-status',
        loadChildren: () => import('./delivery-status/delivery-status.module').then(m => m.JinniDeliveryStatusModule)
      },
      {
        path: 'failure-stage',
        loadChildren: () => import('./failure-stage/failure-stage.module').then(m => m.JinniFailureStageModule)
      },
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.JinniCategoryModule)
      },
      {
        path: 'subcategory',
        loadChildren: () => import('./subcategory/subcategory.module').then(m => m.JinniSubcategoryModule)
      },
      {
        path: 'exam',
        loadChildren: () => import('./exam/exam.module').then(m => m.JinniExamModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.JinniRegionModule)
      },
      {
        path: 'exam-backend',
        loadChildren: () => import('./exam-backend/exam-backend.module').then(m => m.JinniExamBackendModule)
      },
      {
        path: 'offer-type',
        loadChildren: () => import('./offer-type/offer-type.module').then(m => m.JinniOfferTypeModule)
      },
      {
        path: 'cloud-instance',
        loadChildren: () => import('./cloud-instance/cloud-instance.module').then(m => m.JinniCloudInstanceModule)
      },
      {
        path: 'cloud-region',
        loadChildren: () => import('./cloud-region/cloud-region.module').then(m => m.JinniCloudRegionModule)
      },
      {
        path: 'offer',
        loadChildren: () => import('./offer/offer.module').then(m => m.JinniOfferModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class JinniEntityModule {}
