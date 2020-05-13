import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ScheduleService } from 'app/entities/schedule/schedule.service';
import { ISchedule, Schedule } from 'app/shared/model/schedule.model';

describe('Service Tests', () => {
  describe('Schedule Service', () => {
    let injector: TestBed;
    let service: ScheduleService;
    let httpMock: HttpTestingController;
    let elemDefault: ISchedule;
    let expectedResult: ISchedule | ISchedule[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ScheduleService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Schedule(
        0,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate,
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            scheduledsetupstarttime: currentDate.format(DATE_FORMAT),
            actualsetupstarttime: currentDate.format(DATE_FORMAT),
            scheduledsetupendtime: currentDate.format(DATE_FORMAT),
            actualsetupendtime: currentDate.format(DATE_FORMAT),
            scheduledcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            actualcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            scheduledproctorarrivaltime: currentDate.format(DATE_FORMAT),
            actualproctorarrivaltime: currentDate.format(DATE_FORMAT),
            scheduledonboardingstarttime: currentDate.format(DATE_FORMAT),
            actualonboardingstarttime: currentDate.format(DATE_FORMAT),
            scheduledonboardingendtime: currentDate.format(DATE_FORMAT),
            actualonboardingendtime: currentDate.format(DATE_FORMAT),
            scheduledexamstarttime: currentDate.format(DATE_FORMAT),
            actualexamstarttime: currentDate.format(DATE_FORMAT),
            scheduledexamendtime: currentDate.format(DATE_FORMAT),
            actualexamendtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Schedule', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            scheduledsetupstarttime: currentDate.format(DATE_FORMAT),
            actualsetupstarttime: currentDate.format(DATE_FORMAT),
            scheduledsetupendtime: currentDate.format(DATE_FORMAT),
            actualsetupendtime: currentDate.format(DATE_FORMAT),
            scheduledcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            actualcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            scheduledproctorarrivaltime: currentDate.format(DATE_FORMAT),
            actualproctorarrivaltime: currentDate.format(DATE_FORMAT),
            scheduledonboardingstarttime: currentDate.format(DATE_FORMAT),
            actualonboardingstarttime: currentDate.format(DATE_FORMAT),
            scheduledonboardingendtime: currentDate.format(DATE_FORMAT),
            actualonboardingendtime: currentDate.format(DATE_FORMAT),
            scheduledexamstarttime: currentDate.format(DATE_FORMAT),
            actualexamstarttime: currentDate.format(DATE_FORMAT),
            scheduledexamendtime: currentDate.format(DATE_FORMAT),
            actualexamendtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            scheduledsetupstarttime: currentDate,
            actualsetupstarttime: currentDate,
            scheduledsetupendtime: currentDate,
            actualsetupendtime: currentDate,
            scheduledcandidatearrivaltime: currentDate,
            actualcandidatearrivaltime: currentDate,
            scheduledproctorarrivaltime: currentDate,
            actualproctorarrivaltime: currentDate,
            scheduledonboardingstarttime: currentDate,
            actualonboardingstarttime: currentDate,
            scheduledonboardingendtime: currentDate,
            actualonboardingendtime: currentDate,
            scheduledexamstarttime: currentDate,
            actualexamstarttime: currentDate,
            scheduledexamendtime: currentDate,
            actualexamendtime: currentDate
          },
          returnedFromService
        );

        service.create(new Schedule()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Schedule', () => {
        const returnedFromService = Object.assign(
          {
            scheduledsetupstarttime: currentDate.format(DATE_FORMAT),
            actualsetupstarttime: currentDate.format(DATE_FORMAT),
            scheduledsetupendtime: currentDate.format(DATE_FORMAT),
            actualsetupendtime: currentDate.format(DATE_FORMAT),
            scheduledcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            actualcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            scheduledproctorarrivaltime: currentDate.format(DATE_FORMAT),
            actualproctorarrivaltime: currentDate.format(DATE_FORMAT),
            scheduledonboardingstarttime: currentDate.format(DATE_FORMAT),
            actualonboardingstarttime: currentDate.format(DATE_FORMAT),
            scheduledonboardingendtime: currentDate.format(DATE_FORMAT),
            actualonboardingendtime: currentDate.format(DATE_FORMAT),
            scheduledexamstarttime: currentDate.format(DATE_FORMAT),
            actualexamstarttime: currentDate.format(DATE_FORMAT),
            scheduledexamendtime: currentDate.format(DATE_FORMAT),
            actualexamendtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            scheduledsetupstarttime: currentDate,
            actualsetupstarttime: currentDate,
            scheduledsetupendtime: currentDate,
            actualsetupendtime: currentDate,
            scheduledcandidatearrivaltime: currentDate,
            actualcandidatearrivaltime: currentDate,
            scheduledproctorarrivaltime: currentDate,
            actualproctorarrivaltime: currentDate,
            scheduledonboardingstarttime: currentDate,
            actualonboardingstarttime: currentDate,
            scheduledonboardingendtime: currentDate,
            actualonboardingendtime: currentDate,
            scheduledexamstarttime: currentDate,
            actualexamstarttime: currentDate,
            scheduledexamendtime: currentDate,
            actualexamendtime: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Schedule', () => {
        const returnedFromService = Object.assign(
          {
            scheduledsetupstarttime: currentDate.format(DATE_FORMAT),
            actualsetupstarttime: currentDate.format(DATE_FORMAT),
            scheduledsetupendtime: currentDate.format(DATE_FORMAT),
            actualsetupendtime: currentDate.format(DATE_FORMAT),
            scheduledcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            actualcandidatearrivaltime: currentDate.format(DATE_FORMAT),
            scheduledproctorarrivaltime: currentDate.format(DATE_FORMAT),
            actualproctorarrivaltime: currentDate.format(DATE_FORMAT),
            scheduledonboardingstarttime: currentDate.format(DATE_FORMAT),
            actualonboardingstarttime: currentDate.format(DATE_FORMAT),
            scheduledonboardingendtime: currentDate.format(DATE_FORMAT),
            actualonboardingendtime: currentDate.format(DATE_FORMAT),
            scheduledexamstarttime: currentDate.format(DATE_FORMAT),
            actualexamstarttime: currentDate.format(DATE_FORMAT),
            scheduledexamendtime: currentDate.format(DATE_FORMAT),
            actualexamendtime: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            scheduledsetupstarttime: currentDate,
            actualsetupstarttime: currentDate,
            scheduledsetupendtime: currentDate,
            actualsetupendtime: currentDate,
            scheduledcandidatearrivaltime: currentDate,
            actualcandidatearrivaltime: currentDate,
            scheduledproctorarrivaltime: currentDate,
            actualproctorarrivaltime: currentDate,
            scheduledonboardingstarttime: currentDate,
            actualonboardingstarttime: currentDate,
            scheduledonboardingendtime: currentDate,
            actualonboardingendtime: currentDate,
            scheduledexamstarttime: currentDate,
            actualexamstarttime: currentDate,
            scheduledexamendtime: currentDate,
            actualexamendtime: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Schedule', () => {
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
