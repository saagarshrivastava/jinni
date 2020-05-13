import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICloudRegion } from 'app/shared/model/cloud-region.model';

type EntityResponseType = HttpResponse<ICloudRegion>;
type EntityArrayResponseType = HttpResponse<ICloudRegion[]>;

@Injectable({ providedIn: 'root' })
export class CloudRegionService {
  public resourceUrl = SERVER_API_URL + 'api/cloud-regions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/cloud-regions';

  constructor(protected http: HttpClient) {}

  create(cloudRegion: ICloudRegion): Observable<EntityResponseType> {
    return this.http.post<ICloudRegion>(this.resourceUrl, cloudRegion, { observe: 'response' });
  }

  update(cloudRegion: ICloudRegion): Observable<EntityResponseType> {
    return this.http.put<ICloudRegion>(this.resourceUrl, cloudRegion, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICloudRegion>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICloudRegion[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICloudRegion[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
