import { User } from 'app/admin/user-management/user-management.model';
import { Cargo } from 'app/entities/enumerations/cargo.model';

export interface IEmpleado extends User {
  id?: number;
  nombre?: string;
  documento?: string;
  direccion?: string;
  email?: string;
  telefono?: string;
  cargo?: Cargo;
  codigoSU?: string | null;
  activo?: boolean | null;
}

export class Empleado implements IEmpleado {
  constructor(
    public id?: number,
    public nombre?: string,
    public documento?: string,
    public direccion?: string,
    public email?: string,
    public telefono?: string,
    public cargo?: Cargo,
    public codigoSU?: string | null,
    public activo?: boolean | null
  ) {
    this.activo = this.activo ?? false;
  }
}

export function getEmpleadoIdentifier(empleado: IEmpleado): number | undefined {
  return empleado.id;
}
