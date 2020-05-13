import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { CloudInstanceDetailComponent } from 'app/entities/cloud-instance/cloud-instance-detail.component';
import { CloudInstance } from 'app/shared/model/cloud-instance.model';

describe('Component Tests', () => {
  describe('CloudInstance Management Detail Component', () => {
    let comp: CloudInstanceDetailComponent;
    let fixture: ComponentFixture<CloudInstanceDetailComponent>;
    const route = ({ data: of({ cloudInstance: new CloudInstance(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CloudInstanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CloudInstanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CloudInstanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load cloudInstance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cloudInstance).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
