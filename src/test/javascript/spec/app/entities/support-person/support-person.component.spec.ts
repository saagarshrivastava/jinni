import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { SupportPersonComponent } from 'app/entities/support-person/support-person.component';
import { SupportPersonService } from 'app/entities/support-person/support-person.service';
import { SupportPerson } from 'app/shared/model/support-person.model';

describe('Component Tests', () => {
  describe('SupportPerson Management Component', () => {
    let comp: SupportPersonComponent;
    let fixture: ComponentFixture<SupportPersonComponent>;
    let service: SupportPersonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SupportPersonComponent]
      })
        .overrideTemplate(SupportPersonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupportPersonComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupportPersonService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SupportPerson(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supportPeople && comp.supportPeople[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
