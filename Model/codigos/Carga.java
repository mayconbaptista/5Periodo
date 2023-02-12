package Model.DTO;

import java.time.LocalDateTime;

class Perecivel extends Etiqueta{
    private LocalDateTime validade;

    public Perecivel(LocalDateTime validade) {
        this.validade = validade;
        super.setTipo("Perecivel");
    }

    public LocalDateTime getValidade() {
        return validade;
    }
} 

class Sensivel extends Etiqueta{
    private float tempoMax;

    /**
     * @param tempoMax
     */

    public Sensivel (float tempoMax){
        this.tempoMax = tempoMax;
        super.setTipo("Sensivel");
    }

    public float getTempoMax(float tempoMax) {
        return this.tempoMax;
    }
}

class Etiqueta {
    private String tipo;

    

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

public class Carga {
    private int numero;
    private float peso;
    private LocalDateTime dataDesembarque;
    private String codigoAgente;
    private Porto portoDest;
    private Etiqueta etiqueta;

    public Carga (float peso, String codigoAgente, Porto portoDest, String tipo, Perecivel perecivel){
        this.peso = peso;
        this.codigoAgente = codigoAgente;
        this.portoDest = portoDest;
        this.etiqueta = perecivel;
    }

    public Carga (float peso, String codigoAgente, Porto portoDest, String tipo, Sensivel sensivel){
        this.peso = peso;
        this.codigoAgente = codigoAgente;
        this.portoDest = portoDest;
        this.etiqueta = sensivel;
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }
    public float getPeso() {
        return peso;
    }
    public void setPeso(float peso) {
        this.peso = peso;
    }
    public LocalDateTime getDataDesembarque() {
        return dataDesembarque;
    }
    public void setDataDesembarque(LocalDateTime dataDesembarque) {
        this.dataDesembarque = dataDesembarque;
    }
    
    public String getCodigoAgente() {
        return codigoAgente;
    }

    public void setCodigoAgente(String codigoAgente) {
        this.codigoAgente = codigoAgente;
    }

    public void setEtiqueta(Etiqueta etiqueta) {
        this.etiqueta = etiqueta;
    }

    public Porto getPortoDest() {
        return portoDest;
    }
    public void setPortoDest(Porto portoDest) {
        this.portoDest = portoDest;
    }

    public Etiqueta getEtiqueta() {
        return etiqueta;
    }
}
