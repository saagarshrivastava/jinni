import { ISubcategory } from 'app/shared/model/subcategory.model';
import { ICategoryInstance } from 'app/shared/model/category-instance.model';

export interface ISubcategoryInstance {
  id?: number;
  categoryinstanceid?: number;
  subcategoryid?: number;
  rank?: number;
  subcategoryid?: ISubcategory;
  categoryinstanceid?: ICategoryInstance;
}

export class SubcategoryInstance implements ISubcategoryInstance {
  constructor(
    public id?: number,
    public categoryinstanceid?: number,
    public subcategoryid?: number,
    public rank?: number,
    public subcategoryid?: ISubcategory,
    public categoryinstanceid?: ICategoryInstance
  ) {}
}
