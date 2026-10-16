package modelo;

public class Producto implements Localizable {
    private int idProducto;
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean activo;
    private Ubicacion ubicacion;

    public Producto() {}

    public Producto(int idProducto, String codigo, String nombre, String descripcion,
                    boolean activo, Ubicacion ubicacion) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activo = activo;
        this.ubicacion = ubicacion;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    @Override
    public String getUbicacionCompleta() {
        if (ubicacion == null) return "Sin ubicación asignada";
        String cajon = (ubicacion.getCajon() == null || ubicacion.getCajon().isBlank())
                ? "-" : ubicacion.getCajon();
        return String.format("Pasillo %s - Estantería %s - Nivel %s - Cajón %s (%s)",
                ubicacion.getPasillo(),
                ubicacion.getEstanteria(),
                ubicacion.getNivel(),
                cajon,
                ubicacion.getDescripcion());
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | " + getUbicacionCompleta();
    }
}