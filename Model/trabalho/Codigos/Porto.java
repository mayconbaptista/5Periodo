package Model.DTO;

import java.util.List;

public class Porto {
    
    private String nome;
    private List<Navio> listaNavios;
    private List<Carga> listaCargas;
    private List<Agente> listaAgentes;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public List<Navio> getListaNavios() {
        return listaNavios;
    }
    public void setListaNavios(Navio navio) {
        this.listaNavios.add(navio);
    }
    public List<Carga> getListaCargas() {
        return listaCargas;
    }
    public void setListaCargas(Carga carga) {
        this.listaCargas.add(carga);
    }
    public List<Agente> getListaAgentes() {
        return listaAgentes;
    }
    public void setListaAgentes(List<Agente> listaAgentes) {
        this.listaAgentes = listaAgentes;
    }

    public void relatorioCargaNaoEmb (){
        for (Carga carga : this.getListaCargas()) {
            System.out.println("Numero da carga: " + carga.getNumero()
                + " - Porto destino: " + carga.getPortoDest().getNome()
                + " - Data maxima para desembarque: " + carga.getDataDesembarque()
                + " - Codigo do agente receptor: " + carga.getCodigoAgente());
        }
    }

    public void relatorioAgente(){
        for(Agente agente: this.getListaAgentes()){
            if(agente.getListaCargas().size() == 0){
                System.out.println("Codigo do agente: " + agente.getCodigo()
                    + "Nome do agente: " + agente.getNome());
            }
        }
    }
}
