import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JinniTestModule } from '../../../test.module';
import { CategoryInstanceComponent } from 'app/entities/category-instance/category-instance.component';
import { CategoryInstanceService } from 'app/entities/category-instance/category-instance.service';
import { CategoryInstance } from 'app/shared/model/category-instance.model';

describe('Component Tests', () => {
  describe('CategoryInstance Management Component', () => {
    let comp: CategoryInstanceComponent;
    let fixture: ComponentFixture<CategoryInstanceComponent>;
    let service: CategoryInstanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JinniTestModule],
        declarations: [CategoryInstanceComponent]
      })
        .overrideTemplate(CategoryInstanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategoryInstanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategoryInstanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CategoryInstance(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categoryInstances && comp.categoryInstances[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
