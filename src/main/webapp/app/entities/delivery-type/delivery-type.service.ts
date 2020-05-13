import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IDeliveryType } from 'app/shared/model/delivery-type.model';

type EntityResponseType = HttpResponse<IDeliveryType>;
type EntityArrayResponseType = HttpResponse<IDeliveryType[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/delivery-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/delivery-types';

  constructor(protected http: HttpClient) {}

  create(deliveryType: IDeliveryType): Observable<EntityResponseType> {
    return this.http.post<IDeliveryType>(this.resourceUrl, deliveryType, { observe: 'response' });
  }

  update(deliveryType: IDeliveryType): Observable<EntityResponseType> {
    return this.http.put<IDeliveryType>(this.resourceUrl, deliveryType, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDeliveryType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeliveryType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDeliveryType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
