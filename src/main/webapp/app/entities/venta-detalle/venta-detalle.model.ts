import { IVenta } from 'app/entities/venta/venta.model';

export interface IVentaDetalle {
  id?: number;
  numeroFactura?: number;
  nombreProducto?: string;
  precioProducto?: number;
  cantidadProducto?: number;
  totalProducto?: number | null;
  venta?: IVenta | null;
}

export class VentaDetalle implements IVentaDetalle {
  constructor(
    public id?: number,
    public numeroFactura?: number,
    public nombreProducto?: string,
    public precioProducto?: number,
    public cantidadProducto?: number,
    public totalProducto?: number | null,
    public venta?: IVenta | null
  ) {}
}

export function getVentaDetalleIdentifier(ventaDetalle: IVentaDetalle): number | undefined {
  return ventaDetalle.id;
}
