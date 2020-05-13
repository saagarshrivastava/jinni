import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { ProctoringInstanceDetailComponent } from 'app/entities/proctoring-instance/proctoring-instance-detail.component';
import { ProctoringInstance } from 'app/shared/model/proctoring-instance.model';

describe('Component Tests', () => {
  describe('ProctoringInstance Management Detail Component', () => {
    let comp: ProctoringInstanceDetailComponent;
    let fixture: ComponentFixture<ProctoringInstanceDetailComponent>;
    const route = ({ data: of({ proctoringInstance: new ProctoringInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ProctoringInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProctoringInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProctoringInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load proctoringInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proctoringInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
