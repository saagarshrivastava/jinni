import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IMajorIncidentSource } from 'app/shared/model/major-incident-source.model';

type EntityResponseType = HttpResponse<IMajorIncidentSource>;
type EntityArrayResponseType = HttpResponse<IMajorIncidentSource[]>;

@Injectable({ providedIn: 'root' })
export class MajorIncidentSourceService {
  public resourceUrl = SERVER_API_URL + 'api/major-incident-sources';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/major-incident-sources';

  constructor(protected http: HttpClient) {}

  create(majorIncidentSource: IMajorIncidentSource): Observable<EntityResponseType> {
    return this.http.post<IMajorIncidentSource>(this.resourceUrl, majorIncidentSource, { observe: 'response' });
  }

  update(majorIncidentSource: IMajorIncidentSource): Observable<EntityResponseType> {
    return this.http.put<IMajorIncidentSource>(this.resourceUrl, majorIncidentSource, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMajorIncidentSource>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMajorIncidentSource[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMajorIncidentSource[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
