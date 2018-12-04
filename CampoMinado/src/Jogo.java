//Função principal do programa, cerebro!
public class Jogo{
    private EstruturaJogo[][] jogo; //Matriz do jogo
    private int jogadas = 0;//Contador de Jogadas (descontinuado) 
    private int contBomba; //Contador de bombas
 
    public Jogo(int sizex, int sizey){ //Incializacao da matriz
        jogo = new EstruturaJogo[sizex][sizey];
        for(int i=0; i<sizex; i++)
            for(int j=0; j<sizey; j++)
                jogo[i][j]= new EstruturaJogo();
    }
 
    public void preProcessamento(int sizex, int sizey, int bomba) { //Inserçao de bombas de forma randomica
        int contador = bomba;
        contBomba = bomba;

        while (contador != 0) {
            int x = (int) (Math.random() * (sizex));
            int y = (int) (Math.random() * (sizey));
	        if (!jogo[x][y].getBomba()) {
	           jogo[x][y].setBomba();
	           contador--;
	       }
        }
 
        contagemBombas(sizex, sizey);
        zerarContadorEmBombas(sizex, sizey);
        print();
    }
 
    private  void zerarContadorEmBombas(int sizex, int sizey){ //Zera todos os contadores de bomba se a coordenada nao tiver bomba
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {
                if (!jogo[i][j].getBomba())
                    continue;
                jogo[i][j].setZeraQuantidadeBombas();
            }
        }
    }
 
    private void contagemBombas(int sizex, int sizey){ //Realiza a contagem de bombas ao redor de uma determinada coordenada
        for (int i = 0; i < sizex; i++) {
            for (int j = 0; j < sizey; j++) {
                if (!jogo[i][j].getBomba()) 
                    continue;
                
              //Se tiver bombas olha nas coordenadas ao seu redor para aumentar a quantidade de bombas
                if (i != (sizex - 1)) {
                    jogo[i + 1][j].setQuantidadeBombas();
                    if(i!=0)
                        jogo[i - 1][j].setQuantidadeBombas();
 
                    if (j != (sizey - 1) && j != 0) {
                        jogo[i][j + 1].setQuantidadeBombas();
                        jogo[i][j - 1].setQuantidadeBombas();
                        jogo[i + 1][j + 1].setQuantidadeBombas();
                        jogo[i + 1][j - 1].setQuantidadeBombas();
                        if(i!=0) {
                            jogo[i - 1][j + 1].setQuantidadeBombas();
                            jogo[i - 1][j - 1].setQuantidadeBombas();
                        }
                    } else if (j == (sizey - 1)) {
                        jogo[i][j - 1].setQuantidadeBombas();
                        jogo[i + 1][j - 1].setQuantidadeBombas();
                        if(i!=0){
                            jogo[i - 1][j - 1].setQuantidadeBombas();
                        }
                    } else {
                        jogo[i][j + 1].setQuantidadeBombas();
                        jogo[i + 1][j + 1].setQuantidadeBombas();
                        if(i!=0){
                            jogo[i - 1][j + 1].setQuantidadeBombas();
                        }
                    }
                } else{
                    if(i!=0){
                        jogo[i - 1][j].setQuantidadeBombas();
                    }
                    if (j != (sizey - 1) && j != 0) {
                        jogo[i][j + 1].setQuantidadeBombas();
                        jogo[i][j - 1].setQuantidadeBombas();
                        if(i!=0) {
                            jogo[i - 1][j + 1].setQuantidadeBombas();
                            jogo[i - 1][j - 1].setQuantidadeBombas();
                        }
                    } else if (j == (sizey - 1)) {
                        jogo[i][j - 1].setQuantidadeBombas();
                        if(i!=0)
                            jogo[i - 1][j - 1].setQuantidadeBombas();
                    } else {
                        jogo[i][j + 1].setQuantidadeBombas();
                        if(i!=(sizex - 1))
                            jogo[i + 1][j + 1].setQuantidadeBombas();
                        if(i!=0)
                            jogo[i - 1][j + 1].setQuantidadeBombas();
                    }
                }
 
            }
        }
    }
 
    //Funca que imprime a tabela (DEBUGGACAO)
    public void print(){
        System.out.println("Jogadas: "+jogadas);
        System.out.println("Contador Bomba: "+contBomba);
        for(int j=0; j<jogo.length; j++){
            for(int i=0; i<jogo.length; i++){
                if(!jogo[i][j].getAtivacao() && !jogo[i][j].getBomba())
                    System.out.printf("%d  |  ",jogo[i][j].getQuantidadeBombas());
                else if(jogo[i][j].getFlag())
                    System.out.printf("%s  |  ", "P");
                else if(jogo[i][j].getBomba())
                    System.out.printf("%s  |  ", "x");
                else
                    System.out.printf("%s  |  ", "o");
            }
            System.out.println();
        }
    }
    
    //Getters
    public boolean getAtivacao(int x, int y) {
    	return jogo[x][y].getAtivacao();
    }
    
    public boolean getFlag(int x, int y) {
    	return jogo[x][y].getFlag();
    }
    
    public boolean getBomba(int x, int y) {
    	return jogo[x][y].getBomba();
    }
    
    public int getQuantidadeBombas(int x, int y) {
    	return jogo[x][y].getQuantidadeBombas();
    }
    
    public int getContBomba() {
    	return contBomba;
    }
    
    //Funcao responsavel pelo jogo
    public boolean play(int cordX, int cordY){
        jogadas++;
        if(!jogo[cordX][cordY].getAtivacao() && !jogo[cordX][cordY].getFlag()){ //Se nao estiver ativado ou com flag, pode ser aberto
            if(jogo[cordX][cordY].getBomba()) { //Verifica se tem bomba
                jogo[cordX][cordY].setAtivacao();
                return true; //Fim de jogo
            }else if(jogo[cordX][cordY].getQuantidadeBombas()!=0){ //Mostra a quantidade de bombas ao redor
                jogo[cordX][cordY].setAtivacao();
                return false;
            }else{
                expansao(cordX, cordY); //Se for zero, expande
            }
        }
        return false; //Show de Bola, o show continua
    }
 
    public void expansao(int cordX, int cordY){ //Expansao de forma recursiva
        try {
            if (jogo[cordX][cordY].getQuantidadeBombas() != 0) {
                jogo[cordX][cordY].setAtivacao();
                return;
            }else if(jogo[cordX][cordY].getAtivacao()){
                return;
            }
            System.out.println(cordX+" "+cordY+" | "+jogo[cordX][cordY].getQuantidadeBombas() );
                jogo[cordX][cordY].setAtivacao();
                expansao((1+cordX),cordY);
                expansao((cordX),(1+cordY));
                expansao(cordX,(cordY-1));
                expansao((cordX-1),cordY);
                
                expansao((1+cordX),cordY+1);
                expansao((cordX-1),(1+cordY));
                expansao(cordX-1,(cordY-1));
                expansao((cordX+1),cordY-1);
        }catch (java.lang.ArrayIndexOutOfBoundsException exc){
            return;
        }
    }
 
    public void flag(int cordX, int cordY){ //Funcao para colocar uma flag em uma posicao
        jogadas++;
        if(!jogo[cordX][cordY].getFlag()) {
	        if(contBomba!=0) {
	        	contBomba--;
	        	jogo[cordX][cordY].setFlag();
	    	}
    	}else {
        	contBomba++;
    		jogo[cordX][cordY].unsetFlag();
    	}
    }
    
    public void aberturaGeral(int x, int y) { //Funcao de ativação geral quando o jogo acaba
    	for(int i=0; i<y; i++) {
    		for(int j=0;j<y;j++) {
    			if(!jogo[i][j].getFlag())
    				jogo[i][j].setAtivacao();
    		}
    	}
    }
    
    public boolean verificacao(int sizex, int sizey, int bombas){ //Verifica se o jogo esta ganho
        int cont=0;
 
        for(int i=0; i<jogo.length; i++){
            for(int j=0; j<jogo.length; j++) {
                if(jogo[i][j].getAtivacao())
                    cont++;
            }
        }
        return ((sizex*sizey)-cont == bombas);
    }
 
}