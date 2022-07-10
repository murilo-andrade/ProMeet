package br.com.promeet.app;

import androidx.annotation.NonNull;

public class EventoValue implements java.io.Serializable {
    private Long id;
    private String evento;
    private String usuario;
    private Long grupoID;
    private String data;
    private String hora;
    private String local;

    @NonNull
    @Override
    public String toString(){
        return getEvento();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getGrupoID() {
        return grupoID;
    }

    public void setGrupoID(Long grupoID) {
        this.grupoID = grupoID;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
