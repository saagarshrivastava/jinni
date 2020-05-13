import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ProctoringInstanceService } from 'app/entities/proctoring-instance/proctoring-instance.service';
import { IProctoringInstance, ProctoringInstance } from 'app/shared/model/proctoring-instance.model';

describe('Service Tests', () => {
  describe('ProctoringInstance Service', () => {
    let injector: TestBed;
    let service: ProctoringInstanceService;
    let httpMock: HttpTestingController;
    let elemDefault: IProctoringInstance;
    let expectedResult: IProctoringInstance | IProctoringInstance[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProctoringInstanceService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ProctoringInstance(0, currentDate, currentDate, 0, 0, 'AAAAAAA', 'AAAAAAA', false, false, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            proctorstarttime: currentDate.format(DATE_FORMAT),
            proctorendtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProctoringInstance', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            proctorstarttime: currentDate.format(DATE_FORMAT),
            proctorendtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            proctorstarttime: currentDate,
            proctorendtime: currentDate
          },
          returnedFromService
        );

        service.create(new ProctoringInstance()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProctoringInstance', () => {
        const returnedFromService = Object.assign(
          {
            proctorstarttime: currentDate.format(DATE_FORMAT),
            proctorendtime: currentDate.format(DATE_FORMAT),
            proctorid: 1,
            sessionid: 1,
            sessionnotes: 'BBBBBB',
            proctorchat: 'BBBBBB',
            suspended: true,
            terminated: true,
            numberofbreaks: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            proctorstarttime: currentDate,
            proctorendtime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProctoringInstance', () => {
        const returnedFromService = Object.assign(
          {
            proctorstarttime: currentDate.format(DATE_FORMAT),
            proctorendtime: currentDate.format(DATE_FORMAT),
            proctorid: 1,
            sessionid: 1,
            sessionnotes: 'BBBBBB',
            proctorchat: 'BBBBBB',
            suspended: true,
            terminated: true,
            numberofbreaks: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            proctorstarttime: currentDate,
            proctorendtime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ProctoringInstance', () => {
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
