import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { FailureStageComponent } from 'app/entities/failure-stage/failure-stage.component';
import { FailureStageService } from 'app/entities/failure-stage/failure-stage.service';
import { FailureStage } from 'app/shared/model/failure-stage.model';

describe('Component Tests', () => {
  describe('FailureStage Management Component', () => {
    let comp: FailureStageComponent;
    let fixture: ComponentFixture<FailureStageComponent>;
    let service: FailureStageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [FailureStageComponent]
      })
        .overrideTemplate(FailureStageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FailureStageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FailureStageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FailureStage(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.failureStages && comp.failureStages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
