import { ICategory } from 'app/shared/model/category.model';

export interface ISubcategory {
  id?: number;
  code?: string;
  description?: string;
  categoryid?: number;
  categoryid?: ICategory;
}

export class Subcategory implements ISubcategory {
  constructor(
    public id?: number,
    public code?: string,
    public description?: string,
    public categoryid?: number,
    public categoryid?: ICategory
  ) {}
}
