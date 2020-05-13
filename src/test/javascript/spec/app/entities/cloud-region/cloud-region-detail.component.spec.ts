import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { CloudRegionDetailComponent } from 'app/entities/cloud-region/cloud-region-detail.component';
import { CloudRegion } from 'app/shared/model/cloud-region.model';

describe('Component Tests', () => {
  describe('CloudRegion Management Detail Component', () => {
    let comp: CloudRegionDetailComponent;
    let fixture: ComponentFixture<CloudRegionDetailComponent>;
    const route = ({ data: of({ cloudRegion: new CloudRegion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CloudRegionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CloudRegionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CloudRegionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cloudRegion on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cloudRegion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
