import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDeliveryStatus } from 'app/shared/model/delivery-status.model';

type EntityResponseType = HttpResponse<IDeliveryStatus>;
type EntityArrayResponseType = HttpResponse<IDeliveryStatus[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryStatusService {
  public resourceUrl = SERVER_API_URL + 'api/delivery-statuses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/delivery-statuses';

  constructor(protected http: HttpClient) {}

  create(deliveryStatus: IDeliveryStatus): Observable<EntityResponseType> {
    return this.http.post<IDeliveryStatus>(this.resourceUrl, deliveryStatus, { observe: 'response' });
  }

  update(deliveryStatus: IDeliveryStatus): Observable<EntityResponseType> {
    return this.http.put<IDeliveryStatus>(this.resourceUrl, deliveryStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeliveryStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeliveryStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeliveryStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
