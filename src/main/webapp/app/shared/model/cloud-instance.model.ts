import { ICloudRegion } from 'app/shared/model/cloud-region.model';
import { IExamBackend } from 'app/shared/model/exam-backend.model';

export interface ICloudInstance {
  id?: number;
  code?: string;
  description?: string;
  cloudregionid?: number;
  exambackendid?: number;
  cloudregionid?: ICloudRegion;
  exambackendid?: IExamBackend;
}

export class CloudInstance implements ICloudInstance {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public cloudregionid?: number,
    public exambackendid?: number,
    public cloudregionid?: ICloudRegion,
    public exambackendid?: IExamBackend
  ) {}
}
