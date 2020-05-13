import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { OfferTypeComponent } from 'app/entities/offer-type/offer-type.component';
import { OfferTypeService } from 'app/entities/offer-type/offer-type.service';
import { OfferType } from 'app/shared/model/offer-type.model';

describe('Component Tests', () => {
  describe('OfferType Management Component', () => {
    let comp: OfferTypeComponent;
    let fixture: ComponentFixture<OfferTypeComponent>;
    let service: OfferTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [OfferTypeComponent]
      })
        .overrideTemplate(OfferTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OfferTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OfferTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OfferType(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.offerTypes && comp.offerTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
