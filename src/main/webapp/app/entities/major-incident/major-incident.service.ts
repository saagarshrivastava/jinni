import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IMajorIncident } from 'app/shared/model/major-incident.model';

type EntityResponseType = HttpResponse<IMajorIncident>;
type EntityArrayResponseType = HttpResponse<IMajorIncident[]>;

@Injectable({ providedIn: 'root' })
export class MajorIncidentService {
  public resourceUrl = SERVER_API_URL + 'api/major-incidents';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/major-incidents';

  constructor(protected http: HttpClient) {}

  create(majorIncident: IMajorIncident): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(majorIncident);
    return this.http
      .post<IMajorIncident>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(majorIncident: IMajorIncident): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(majorIncident);
    return this.http
      .put<IMajorIncident>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMajorIncident>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMajorIncident[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMajorIncident[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(majorIncident: IMajorIncident): IMajorIncident {
    const copy: IMajorIncident = Object.assign({}, majorIncident, {
      starttime: majorIncident.starttime && majorIncident.starttime.isValid() ? majorIncident.starttime.format(DATE_FORMAT) : undefined,
      endtime: majorIncident.endtime && majorIncident.endtime.isValid() ? majorIncident.endtime.format(DATE_FORMAT) : undefined,
      date: majorIncident.date && majorIncident.date.isValid() ? majorIncident.date.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.starttime = res.body.starttime ? moment(res.body.starttime) : undefined;
      res.body.endtime = res.body.endtime ? moment(res.body.endtime) : undefined;
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((majorIncident: IMajorIncident) => {
        majorIncident.starttime = majorIncident.starttime ? moment(majorIncident.starttime) : undefined;
        majorIncident.endtime = majorIncident.endtime ? moment(majorIncident.endtime) : undefined;
        majorIncident.date = majorIncident.date ? moment(majorIncident.date) : undefined;
      });
    }
    return res;
  }
}
