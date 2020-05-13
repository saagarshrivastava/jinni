import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SupportInstanceService } from 'app/entities/support-instance/support-instance.service';
import { ISupportInstance, SupportInstance } from 'app/shared/model/support-instance.model';

describe('Service Tests', () => {
  describe('SupportInstance Service', () => {
    let injector: TestBed;
    let service: SupportInstanceService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupportInstance;
    let expectedResult: ISupportInstance | ISupportInstance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SupportInstanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SupportInstance(0, currentDate, currentDate, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SupportInstance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            starttime: currentDate,
            endtime: currentDate
          },
          returnedFromService
        );

        service.create(new SupportInstance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SupportInstance', () => {
        const returnedFromService = Object.assign(
          {
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT),
            chatlogs: 'BBBBBB',
            sessionid: 1,
            supportpersonid: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            starttime: currentDate,
            endtime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SupportInstance', () => {
        const returnedFromService = Object.assign(
          {
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT),
            chatlogs: 'BBBBBB',
            sessionid: 1,
            supportpersonid: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            starttime: currentDate,
            endtime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SupportInstance', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
