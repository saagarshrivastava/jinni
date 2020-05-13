import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { CloudRegionComponent } from 'app/entities/cloud-region/cloud-region.component';
import { CloudRegionService } from 'app/entities/cloud-region/cloud-region.service';
import { CloudRegion } from 'app/shared/model/cloud-region.model';

describe('Component Tests', () => {
  describe('CloudRegion Management Component', () => {
    let comp: CloudRegionComponent;
    let fixture: ComponentFixture<CloudRegionComponent>;
    let service: CloudRegionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CloudRegionComponent]
      })
        .overrideTemplate(CloudRegionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CloudRegionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CloudRegionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CloudRegion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cloudRegions && comp.cloudRegions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
