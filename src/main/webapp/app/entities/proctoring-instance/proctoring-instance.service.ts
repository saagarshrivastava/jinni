import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IProctoringInstance } from 'app/shared/model/proctoring-instance.model';

type EntityResponseType = HttpResponse<IProctoringInstance>;
type EntityArrayResponseType = HttpResponse<IProctoringInstance[]>;

@Injectable({ providedIn: 'root' })
export class ProctoringInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/proctoring-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/proctoring-instances';

  constructor(protected http: HttpClient) {}

  create(proctoringInstance: IProctoringInstance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proctoringInstance);
    return this.http
      .post<IProctoringInstance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(proctoringInstance: IProctoringInstance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(proctoringInstance);
    return this.http
      .put<IProctoringInstance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IProctoringInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProctoringInstance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IProctoringInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(proctoringInstance: IProctoringInstance): IProctoringInstance {
    const copy: IProctoringInstance = Object.assign({}, proctoringInstance, {
      proctorstarttime:
        proctoringInstance.proctorstarttime && proctoringInstance.proctorstarttime.isValid()
          ? proctoringInstance.proctorstarttime.format(DATE_FORMAT)
          : undefined,
      proctorendtime:
        proctoringInstance.proctorendtime && proctoringInstance.proctorendtime.isValid()
          ? proctoringInstance.proctorendtime.format(DATE_FORMAT)
          : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.proctorstarttime = res.body.proctorstarttime ? moment(res.body.proctorstarttime) : undefined;
      res.body.proctorendtime = res.body.proctorendtime ? moment(res.body.proctorendtime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((proctoringInstance: IProctoringInstance) => {
        proctoringInstance.proctorstarttime = proctoringInstance.proctorstarttime ? moment(proctoringInstance.proctorstarttime) : undefined;
        proctoringInstance.proctorendtime = proctoringInstance.proctorendtime ? moment(proctoringInstance.proctorendtime) : undefined;
      });
    }
    return res;
  }
}
