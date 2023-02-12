package Model.DTO;

import java.util.List;


public class Agente {
    private String nome;
    private String codigo;
    private List<Carga> listaCargas;
    private Porto porto;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Carga> getListaCargas() {
        return listaCargas;
    }
    public void setListaCargas(List<Carga> listaCargas) {
        this.listaCargas = listaCargas;
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Porto getPorto() {
        return porto;
    }
    public void setPorto(Porto porto) {
        this.porto = porto;
    }
    
    public void entregarCarga (){

        for(Carga carga: this.porto.getListaCargas()){
            if(this.verificarCargaAgente(carga)){
                this.porto.getListaCargas().remove(carga);
            }
        }
    }

    public boolean verificarCargaAgente(Carga carga){

        if(carga.getCodigoAgente().equals(this.codigo)){
            this.listaCargas.add(carga);
            return true;
        }
        return false;
    }
}
