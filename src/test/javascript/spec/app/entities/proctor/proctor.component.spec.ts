import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { ProctorComponent } from 'app/entities/proctor/proctor.component';
import { ProctorService } from 'app/entities/proctor/proctor.service';
import { Proctor } from 'app/shared/model/proctor.model';

describe('Component Tests', () => {
  describe('Proctor Management Component', () => {
    let comp: ProctorComponent;
    let fixture: ComponentFixture<ProctorComponent>;
    let service: ProctorService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [ProctorComponent]
      })
        .overrideTemplate(ProctorComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProctorComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProctorService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Proctor(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.proctors && comp.proctors[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
