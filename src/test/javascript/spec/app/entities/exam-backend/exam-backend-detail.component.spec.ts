import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ExamBackendDetailComponent } from 'app/entities/exam-backend/exam-backend-detail.component';
import { ExamBackend } from 'app/shared/model/exam-backend.model';

describe('Component Tests', () => {
  describe('ExamBackend Management Detail Component', () => {
    let comp: ExamBackendDetailComponent;
    let fixture: ComponentFixture<ExamBackendDetailComponent>;
    const route = ({ data: of({ examBackend: new ExamBackend(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ExamBackendDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExamBackendDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExamBackendDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load examBackend on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.examBackend).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
