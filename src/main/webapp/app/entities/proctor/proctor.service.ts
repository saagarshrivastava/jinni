import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IProctor } from 'app/shared/model/proctor.model';

type EntityResponseType = HttpResponse<IProctor>;
type EntityArrayResponseType = HttpResponse<IProctor[]>;

@Injectable({ providedIn: 'root' })
export class ProctorService {
  public resourceUrl = SERVER_API_URL + 'api/proctors';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/proctors';

  constructor(protected http: HttpClient) {}

  create(proctor: IProctor): Observable<EntityResponseType> {
    return this.http.post<IProctor>(this.resourceUrl, proctor, { observe: 'response' });
  }

  update(proctor: IProctor): Observable<EntityResponseType> {
    return this.http.put<IProctor>(this.resourceUrl, proctor, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProctor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProctor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProctor[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
