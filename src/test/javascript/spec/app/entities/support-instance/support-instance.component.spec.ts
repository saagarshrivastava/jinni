import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { SupportInstanceComponent } from 'app/entities/support-instance/support-instance.component';
import { SupportInstanceService } from 'app/entities/support-instance/support-instance.service';
import { SupportInstance } from 'app/shared/model/support-instance.model';

describe('Component Tests', () => {
  describe('SupportInstance Management Component', () => {
    let comp: SupportInstanceComponent;
    let fixture: ComponentFixture<SupportInstanceComponent>;
    let service: SupportInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SupportInstanceComponent]
      })
        .overrideTemplate(SupportInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SupportInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SupportInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SupportInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.supportInstances && comp.supportInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
