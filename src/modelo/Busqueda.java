package modelo;

import java.time.LocalDateTime;

public class Busqueda {
    private int idBusqueda;
    private String textoConsultado;
    private boolean resultado;
    private LocalDateTime fechaHora;
    private int idUsuario;
    private Integer idProducto;

    public Busqueda() {}

    public Busqueda(String textoConsultado, boolean resultado, int idUsuario, Integer idProducto) {
        this.textoConsultado = textoConsultado;
        this.resultado = resultado;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.fechaHora = LocalDateTime.now();
    }

    public int getIdBusqueda() { return idBusqueda; }
    public void setIdBusqueda(int idBusqueda) { this.idBusqueda = idBusqueda; }

    public String getTextoConsultado() { return textoConsultado; }
    public void setTextoConsultado(String textoConsultado) { this.textoConsultado = textoConsultado; }

    public boolean isResultado() { return resultado; }
    public void setResultado(boolean resultado) { this.resultado = resultado; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
}