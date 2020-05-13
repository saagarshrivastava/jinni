import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { FailureStageDetailComponent } from 'app/entities/failure-stage/failure-stage-detail.component';
import { FailureStage } from 'app/shared/model/failure-stage.model';

describe('Component Tests', () => {
  describe('FailureStage Management Detail Component', () => {
    let comp: FailureStageDetailComponent;
    let fixture: ComponentFixture<FailureStageDetailComponent>;
    const route = ({ data: of({ failureStage: new FailureStage(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [FailureStageDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FailureStageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FailureStageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load failureStage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.failureStage).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
