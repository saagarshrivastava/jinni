import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { SubcategoryInstanceComponent } from 'app/entities/subcategory-instance/subcategory-instance.component';
import { SubcategoryInstanceService } from 'app/entities/subcategory-instance/subcategory-instance.service';
import { SubcategoryInstance } from 'app/shared/model/subcategory-instance.model';

describe('Component Tests', () => {
  describe('SubcategoryInstance Management Component', () => {
    let comp: SubcategoryInstanceComponent;
    let fixture: ComponentFixture<SubcategoryInstanceComponent>;
    let service: SubcategoryInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [SubcategoryInstanceComponent]
      })
        .overrideTemplate(SubcategoryInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SubcategoryInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SubcategoryInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new SubcategoryInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.subcategoryInstances && comp.subcategoryInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
