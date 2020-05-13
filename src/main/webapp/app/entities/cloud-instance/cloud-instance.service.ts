import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICloudInstance } from 'app/shared/model/cloud-instance.model';

type EntityResponseType = HttpResponse<ICloudInstance>;
type EntityArrayResponseType = HttpResponse<ICloudInstance[]>;

@Injectable({ providedIn: 'root' })
export class CloudInstanceService {
  public resourceUrl = SERVER_API_URL + 'api/cloud-instances';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/cloud-instances';

  constructor(protected http: HttpClient) {}

  create(cloudInstance: ICloudInstance): Observable<EntityResponseType> {
    return this.http.post<ICloudInstance>(this.resourceUrl, cloudInstance, { observe: 'response' });
  }

  update(cloudInstance: ICloudInstance): Observable<EntityResponseType> {
    return this.http.put<ICloudInstance>(this.resourceUrl, cloudInstance, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICloudInstance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICloudInstance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICloudInstance[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
