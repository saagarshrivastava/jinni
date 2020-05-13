import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { SessionBreaksComponent } from 'app/entities/session-breaks/session-breaks.component';
import { SessionBreaksService } from 'app/entities/session-breaks/session-breaks.service';
import { SessionBreaks } from 'app/shared/model/session-breaks.model';

describe('Component Tests', () => {
  describe('SessionBreaks Management Component', () => {
    let comp: SessionBreaksComponent;
    let fixture: ComponentFixture<SessionBreaksComponent>;
    let service: SessionBreaksService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SessionBreaksComponent]
      })
        .overrideTemplate(SessionBreaksComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SessionBreaksComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SessionBreaksService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SessionBreaks(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.sessionBreaks && comp.sessionBreaks[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
