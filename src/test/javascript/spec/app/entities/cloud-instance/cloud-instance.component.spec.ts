import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { CloudInstanceComponent } from 'app/entities/cloud-instance/cloud-instance.component';
import { CloudInstanceService } from 'app/entities/cloud-instance/cloud-instance.service';
import { CloudInstance } from 'app/shared/model/cloud-instance.model';

describe('Component Tests', () => {
  describe('CloudInstance Management Component', () => {
    let comp: CloudInstanceComponent;
    let fixture: ComponentFixture<CloudInstanceComponent>;
    let service: CloudInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CloudInstanceComponent]
      })
        .overrideTemplate(CloudInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CloudInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CloudInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cloudInstances && comp.cloudInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
