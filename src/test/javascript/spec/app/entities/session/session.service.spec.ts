import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionService } from 'app/entities/session/session.service';
import { ISession, Session } from 'app/shared/model/session.model';

describe('Service Tests', () => {
  describe('Session Service', () => {
    let injector: TestBed;
    let service: SessionService;
    let httpMock: HttpTestingController;
    let elemDefault: ISession;
    let expectedResult: ISession | ISession[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SessionService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Session(0, 0, 0, 0, 0, 0, 0, 0, 0, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Session', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Session()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Session', () => {
        const returnedFromService = Object.assign(
          {
            scheduleid: 1,
            candidateid: 1,
            locationid: 1,
            examtypeid: 1,
            deliverytypeid: 1,
            deliverystatusid: 1,
            examid: 1,
            exambackendid: 1,
            reservationid: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Session', () => {
        const returnedFromService = Object.assign(
          {
            scheduleid: 1,
            candidateid: 1,
            locationid: 1,
            examtypeid: 1,
            deliverytypeid: 1,
            deliverystatusid: 1,
            examid: 1,
            exambackendid: 1,
            reservationid: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Session', () => {
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
