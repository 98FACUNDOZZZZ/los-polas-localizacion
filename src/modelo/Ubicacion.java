package modelo;

public class Ubicacion {
    private int idUbicacion;
    private String pasillo;
    private String estanteria;
    private String nivel;
    private String cajon;
    private String descripcion;

    public Ubicacion() {}

    public Ubicacion(int idUbicacion, String pasillo, String estanteria,
                     String nivel, String cajon, String descripcion) {
        this.idUbicacion = idUbicacion;
        this.pasillo = pasillo;
        this.estanteria = estanteria;
        this.nivel = nivel;
        this.cajon = cajon;
        this.descripcion = descripcion;
    }

    public int getIdUbicacion() { return idUbicacion; }
    public void setIdUbicacion(int idUbicacion) { this.idUbicacion = idUbicacion; }

    public String getPasillo() { return pasillo; }
    public void setPasillo(String pasillo) { this.pasillo = pasillo; }

    public String getEstanteria() { return estanteria; }
    public void setEstanteria(String estanteria) { this.estanteria = estanteria; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getCajon() { return cajon; }
    public void setCajon(String cajon) { this.cajon = cajon; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override
    public String toString() {
        String caj = (cajon == null || cajon.isBlank()) ? "-" : cajon;
        return idUbicacion + " | " + pasillo + " / " + estanteria + " / " + nivel + " / " + caj
                + " (" + descripcion + ")";
    }
}