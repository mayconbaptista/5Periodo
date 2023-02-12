package Model.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class Navio {
    
    private String nome;
    private float capacidade;
    private LocalDateTime dataChegada;
    private List<Carga> listaCargas;
    private Porto portoOrig;
    private Porto portoDest;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public float getCapacidade() {
        return capacidade;
    }
    public void setCapacidade(float capacidade) {
        this.capacidade = capacidade;
    }
    public LocalDateTime getDataChegada() {
        return dataChegada;
    }
    public void setDataChegada(LocalDateTime dataChegada) {
        this.dataChegada = dataChegada;
    }
    public List<Carga> getListaCargas() {
        return listaCargas;
    }
    public void setListaCargas(List<Carga> listaCargas) {
        for(Carga carga: listaCargas){
            this.listaCargas.add(carga);
        }
    }
    public Porto getPortoOrig() {
        return portoOrig;
    }
    public void setPortoOrig(Porto portoOrig) {
        this.portoOrig = portoOrig;
    }
    public Porto getPortoDest() {
        return portoDest;
    }
    public void setPortoDest(Porto portoDest) {
        this.portoDest = portoDest;
    }

    public void embarcarCargas (){

        float peso = 0;

        for(Carga carga: this.portoOrig.getListaCargas()){
            if(peso >= this.capacidade)
                break;
            else if(verificarCargaNavio(carga)){
                this.portoOrig.getListaCargas().remove(carga);
                peso += carga.getPeso();
            }
        }

        this.relatorioCargaEmb();
    }

    public Boolean verificarCargaNavio(Carga carga){

        if(carga.getPortoDest().getNome().equals(this.portoDest.getNome())){
            if(carga.getDataDesembarque().compareTo(this.getDataChegada()) < 1){
                this.listaCargas.add(carga);
                return true;
            }
        }

        return false;
    }

    public void desembarcarCarga (){

        for(Carga carga : this.getListaCargas()){
            this.getPortoDest().setListaCargas(carga);
        }

        this.listaCargas.clear();
    }

    public void relatorioCargaEmb (){
        for(Carga carga: this.listaCargas){
            System.out.println("Numero da carga: " + carga.getNumero() 
                + " - Porto destino " + carga.getPortoDest().getNome()
                + " - Navio " + this.getNome()
                + " - Data maxima desbarque carga " + carga.getDataDesembarque()
                + " - Data prevista de chegada" + this.getDataChegada());
        }
    }

    public void relatorioNavio (){

        System.out.println("Nome: " + this.getNome()
            + " - Capacidade maxima: " + this.getCapacidade()
            + " - Porto destino: " + this.portoDest.getNome()
            + " - Chega prevista: " + this.getDataChegada());
    }
}
