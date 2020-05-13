import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { SessionBreaksDetailComponent } from 'app/entities/session-breaks/session-breaks-detail.component';
import { SessionBreaks } from 'app/shared/model/session-breaks.model';

describe('Component Tests', () => {
  describe('SessionBreaks Management Detail Component', () => {
    let comp: SessionBreaksDetailComponent;
    let fixture: ComponentFixture<SessionBreaksDetailComponent>;
    const route = ({ data: of({ sessionBreaks: new SessionBreaks(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SessionBreaksDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SessionBreaksDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SessionBreaksDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sessionBreaks on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sessionBreaks).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
