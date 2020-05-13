import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JinniTestModule } from '../../../test.module';
import { OfferTypeDetailComponent } from 'app/entities/offer-type/offer-type-detail.component';
import { OfferType } from 'app/shared/model/offer-type.model';

describe('Component Tests', () => {
  describe('OfferType Management Detail Component', () => {
    let comp: OfferTypeDetailComponent;
    let fixture: ComponentFixture<OfferTypeDetailComponent>;
    const route = ({ data: of({ offerType: new OfferType(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [OfferTypeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OfferTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OfferTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load offerType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerType).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
