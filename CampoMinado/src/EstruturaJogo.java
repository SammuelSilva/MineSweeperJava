

public class EstruturaJogo{
    private boolean bomba;
    private boolean flag ;
    private boolean ativacao;
    private int quantidadeBombas;
 
    public EstruturaJogo(){
        this.bomba = false;
        this.flag = false;
        this.ativacao = false;
        this.quantidadeBombas = 0;
    }
 
    public void setBomba() {
        this.bomba = true;
    }
 
    public void setQuantidadeBombas() {
        this.quantidadeBombas += 1;
    }
 
    public void setZeraQuantidadeBombas() {
        this.quantidadeBombas = 9;
    }
 
    public void setFlag(){
        this.flag=true;
    }
    
    public void unsetFlag(){
        this.flag=false;
    }
    
    public void setAtivacao(){
        this.ativacao=true;
    }
 
    public final boolean getAtivacao() {
        return ativacao;
    }
 
    public final int getQuantidadeBombas() {
        return quantidadeBombas;
    }
 
    public final boolean getBomba() {
        return bomba;
    }
 
    public final boolean getFlag() {
        return flag;
    }
}