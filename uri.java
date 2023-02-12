import java.util.*;

class camisa {
    private String nome;
    private String cor;
    private String tamanho;

    camisa (String nome, String cor, String tamanho){
        this.nome = nome;
        this.cor = cor;
        this.tamanho = tamanho;
    }

    public String getNome(){
        return this.nome;
    }
    public String getCor (){
        return this.cor;
    }
    public String getTamanho (){
        return this.tamanho;
    }
}

class comparador implements Comparator<camisa>{

    @Override
    public int compare(camisa arg0, camisa arg1) {
        // TODO Auto-generated method stub
        
        if(arg0.getCor().equals(arg1.getCor())){
            if(arg0.getTamanho().equals(arg1.getTamanho())){
                if(arg0.getNome().compareTo(arg1.getNome())  < 0){
                    return -1;
                }else if(arg0.getNome().compareTo(arg1.getNome()) > 0){
                    return 1;
                }
                else{
                    return 0;
                }
            }else if(arg0.getTamanho().compareTo(arg1.getTamanho()) < 0){
                return 1;
            }else{
                return -1;
            }
        }else if(arg0.getCor().compareTo(arg1.getCor()) < 0){
            return -1;
        }else{
            return 1;
        }
    }
}

public class uri {

    public static void main (String [] args){

        int casos;
        Scanner in = new Scanner(System.in);

        while(in.hasNext()){
            casos = in.nextInt();

            if(in.hasNext()){
                in.nextLine();
            }

            if(casos == 0) break;

            PriorityQueue <camisa> pQueue = new PriorityQueue<camisa>(casos, new comparador());
            String cor, aux[];
            
            for (int i = 0; i < casos; i++){

                cor = in.nextLine();
                aux = in.nextLine().split(" "); 

                pQueue.add(new camisa(cor, aux[0], aux[1]));
            }

            for (camisa item : pQueue){
                System.out.println(item.getCor() + " " + item.getTamanho() + " " + item.getNome());
            }
            System.out.println();
        }

        in.close();
    }   
}
