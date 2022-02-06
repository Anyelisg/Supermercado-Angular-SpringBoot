import { IVenta } from 'app/entities/venta/venta.model';

export interface IEmpresa {
  id?: number;
  nombre?: string | null;
  nif?: string | null;
  direccion?: string | null;
  telefono?: string | null;
  ventas?: IVenta[] | null;
}

export class Empresa implements IEmpresa {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public nif?: string | null,
    public direccion?: string | null,
    public telefono?: string | null,
    public ventas?: IVenta[] | null
  ) {}
}

export function getEmpresaIdentifier(empresa: IEmpresa): number | undefined {
  return empresa.id;
}
