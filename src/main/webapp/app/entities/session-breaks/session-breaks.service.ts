import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISessionBreaks } from 'app/shared/model/session-breaks.model';

type EntityResponseType = HttpResponse<ISessionBreaks>;
type EntityArrayResponseType = HttpResponse<ISessionBreaks[]>;

@Injectable({ providedIn: 'root' })
export class SessionBreaksService {
  public resourceUrl = SERVER_API_URL + 'api/session-breaks';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/session-breaks';

  constructor(protected http: HttpClient) {}

  create(sessionBreaks: ISessionBreaks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sessionBreaks);
    return this.http
      .post<ISessionBreaks>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(sessionBreaks: ISessionBreaks): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(sessionBreaks);
    return this.http
      .put<ISessionBreaks>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISessionBreaks>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISessionBreaks[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISessionBreaks[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(sessionBreaks: ISessionBreaks): ISessionBreaks {
    const copy: ISessionBreaks = Object.assign({}, sessionBreaks, {
      starttime: sessionBreaks.starttime && sessionBreaks.starttime.isValid() ? sessionBreaks.starttime.format(DATE_FORMAT) : undefined,
      endtime: sessionBreaks.endtime && sessionBreaks.endtime.isValid() ? sessionBreaks.endtime.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.starttime = res.body.starttime ? moment(res.body.starttime) : undefined;
      res.body.endtime = res.body.endtime ? moment(res.body.endtime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((sessionBreaks: ISessionBreaks) => {
        sessionBreaks.starttime = sessionBreaks.starttime ? moment(sessionBreaks.starttime) : undefined;
        sessionBreaks.endtime = sessionBreaks.endtime ? moment(sessionBreaks.endtime) : undefined;
      });
    }
    return res;
  }
}
