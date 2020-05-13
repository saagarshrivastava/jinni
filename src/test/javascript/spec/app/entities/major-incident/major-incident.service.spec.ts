import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { MajorIncidentService } from 'app/entities/major-incident/major-incident.service';
import { IMajorIncident, MajorIncident } from 'app/shared/model/major-incident.model';

describe('Service Tests', () => {
  describe('MajorIncident Service', () => {
    let injector: TestBed;
    let service: MajorIncidentService;
    let httpMock: HttpTestingController;
    let elemDefault: IMajorIncident;
    let expectedResult: IMajorIncident | IMajorIncident[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MajorIncidentService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new MajorIncident(0, 0, currentDate, currentDate, currentDate, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT),
            date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MajorIncident', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT),
            date: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            starttime: currentDate,
            endtime: currentDate,
            date: currentDate
          },
          returnedFromService
        );

        service.create(new MajorIncident()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MajorIncident', () => {
        const returnedFromService = Object.assign(
          {
            majorincidentsourceid: 1,
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT),
            date: currentDate.format(DATE_FORMAT),
            details: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            starttime: currentDate,
            endtime: currentDate,
            date: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MajorIncident', () => {
        const returnedFromService = Object.assign(
          {
            majorincidentsourceid: 1,
            starttime: currentDate.format(DATE_FORMAT),
            endtime: currentDate.format(DATE_FORMAT),
            date: currentDate.format(DATE_FORMAT),
            details: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            starttime: currentDate,
            endtime: currentDate,
            date: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MajorIncident', () => {
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
