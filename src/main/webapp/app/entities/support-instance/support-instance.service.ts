import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISupportInstance } from 'app/shared/model/support-instance.model';

type EntityResponseType = HttpResponse<ISupportInstance>;
type EntityArrayResponseType = HttpResponse<ISupportInstance[]>;

@Injectable({ providedIn: 'root' })
export class SupportInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/support-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/support-instances';

  constructor(protected http: HttpClient) {}

  create(supportInstance: ISupportInstance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supportInstance);
    return this.http
      .post<ISupportInstance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(supportInstance: ISupportInstance): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(supportInstance);
    return this.http
      .put<ISupportInstance>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISupportInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupportInstance[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISupportInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(supportInstance: ISupportInstance): ISupportInstance {
    const copy: ISupportInstance = Object.assign({}, supportInstance, {
      starttime:
        supportInstance.starttime && supportInstance.starttime.isValid() ? supportInstance.starttime.format(DATE_FORMAT) : undefined,
      endtime: supportInstance.endtime && supportInstance.endtime.isValid() ? supportInstance.endtime.format(DATE_FORMAT) : undefined
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
      res.body.forEach((supportInstance: ISupportInstance) => {
        supportInstance.starttime = supportInstance.starttime ? moment(supportInstance.starttime) : undefined;
        supportInstance.endtime = supportInstance.endtime ? moment(supportInstance.endtime) : undefined;
      });
    }
    return res;
  }
}
