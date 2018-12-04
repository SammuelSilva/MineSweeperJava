import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tabuleiro {
	//Variaveis para imagens
	private BufferedImage box;
	private BufferedImage openedBox;
	private BufferedImage flagImg;
	private BufferedImage bombImg;
	private BufferedImage numero1;
	private BufferedImage numero2;
	private BufferedImage numero3;
	private BufferedImage numero4;
	private BufferedImage numero5;
	private BufferedImage numero6;
	private BufferedImage numero7;
	private BufferedImage numero8;
	private BufferedImage num1;
	private BufferedImage num2;
	private BufferedImage num3;
	private BufferedImage num4;
	private BufferedImage num5;
	private BufferedImage num6;
	private BufferedImage num7;
	private BufferedImage num8;
	private BufferedImage num9;
	private BufferedImage num0;
	private BufferedImage encima;
	private BufferedImage flagImgEncima;
	private BufferedImage vazio;
	private BufferedImage BombaFalse;
	//Coordenadas
	private int x;
	private int y;
	//Auxiliares para formaçao da tela
	public int yAux = 155;
	public int xAux = 42;
	
	public Tabuleiro(int x, int y, int dimLargTela, int dimAltTela) { //Construtor que armazena as coordenadas e as imagens
		this.x = x; this.y = y;
		box = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/normal.png"), dimLargTela, dimAltTela);
		openedBox = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/pressed.png"), dimLargTela, dimAltTela);
		vazio = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/pressedEmpty.png"), dimLargTela, dimAltTela);
		flagImg = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/flag.png"), dimLargTela, dimAltTela);
		bombImg = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/bomb.png"), dimLargTela, dimAltTela);
		numero1 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number1.png"), dimLargTela, dimAltTela);
		numero2 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number2.png"), dimLargTela, dimAltTela);
		numero3 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number3.png"), dimLargTela, dimAltTela);
		numero4 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number4.png"), dimLargTela, dimAltTela);
		numero5 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number5.png"), dimLargTela, dimAltTela);
		numero6 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number6.png"), dimLargTela, dimAltTela);
		numero7 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number7.png"), dimLargTela, dimAltTela);
		numero8 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/number8.png"), dimLargTela, dimAltTela);
		num1 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B1.png"), dimLargTela - 5, dimAltTela - 5);
		num2 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B2.png"), dimLargTela - 5, dimAltTela - 5);
		num3 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B3.png"), dimLargTela - 5, dimAltTela - 5);
		num4 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B4.png"), dimLargTela - 5, dimAltTela - 5);
		num5 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B5.png"), dimLargTela - 5, dimAltTela - 5);
		num6 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B6.png"), dimLargTela - 5, dimAltTela - 5);
		num7 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B7.png"), dimLargTela - 5, dimAltTela - 5);
		num8 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B8.png"), dimLargTela - 5, dimAltTela - 5);
		num9 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B9.png"), dimLargTela - 5, dimAltTela - 5);
		num0 = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/B0.png"), dimLargTela - 5, dimAltTela - 5);
		encima = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/encima.png"), dimLargTela + 10, dimAltTela + 10);
		flagImgEncima = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/FlagEncima.png"), dimLargTela + 10, dimAltTela + 10);
		BombaFalse = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/BombaFalse.png"), dimLargTela, dimAltTela);
	}
	
	public void contadorBombas(Jogo jogo, Graphics g) { //Atualizador do contador de bombas, até 199 bombas
		int aux = jogo.getContBomba();
		if(aux >= 100 && aux <200) {
			CarregadorNumImg(g,1, 75, 57);
			aux-=100;
		}else
			CarregadorNumImg(g,0, 75, 57);
		aux/=10;
		if(aux != 0) {
			CarregadorNumImg(g,aux, 116, 57);
			aux = jogo.getContBomba()%10;
			CarregadorNumImg(g,aux,158,57);
			return;
		}
		CarregadorNumImg(g,0, 116, 57);
		CarregadorNumImg(g,jogo.getContBomba(), 158, 57);
	}
	
	public void CarregadorNumImg(Graphics g,int aux, int posx, int posy) { //Carrega o numero refente a uma centena/dezena/unidade
		switch(aux){
	     	case 1:
	     		g.drawImage(num1, posx, posy, null);
	             break;
	         case 2:
	             g.drawImage(num2, posx, posy, null);
	             break;
	         case 3:
	             g.drawImage(num3, posx, posy, null);
	             break;
	         case 4:
	             g.drawImage(num4, posx, posy, null);
	             break;
	         case 5:
	             g.drawImage(num5, posx, posy, null);
	             break;
	         case 6:
	             g.drawImage(num6, posx, posy, null);
	             break;
	         case 7:
	              g.drawImage(num7, posx, posy, null);
	             break;
	         case 8:
	             g.drawImage(num8, posx, posy, null);
	             break; 
	         case 9:
	             g.drawImage(num9, posx, posy, null);
	             break;
	         case 0:
	             g.drawImage(num0, posx, posy, null);
	             break;
	
		}
	}
	
	public void draw(Graphics g, Jogo jogo, int altura, int largura, boolean condicaoAtual){ //Desenha o tablado
		
		contadorBombas(jogo, g);
		if(!jogo.getAtivacao(x, y)) {  //Verifica se nao foi selecionado ainda
			if(jogo.getFlag(x, y)) { //Verifica se tem flag
				if(condicaoAtual) { //Verifica se o jogo acabou
					if(!jogo.getBomba(x, y)) //Se a posicao com flag nao tiver bomba
						g.drawImage(BombaFalse, x * largura + xAux, y*altura + yAux, null); //Insere Bomba cortada
				}else {
					g.drawImage(flagImg, x * largura + xAux, y*altura + yAux, null); //Insere Flag
				}	
			}
			else g.drawImage(box, x * largura + xAux, y*altura + yAux, null); //Desenha um bloco normal
		}
		else {
			if(jogo.getBomba(x, y)) //Desenha se tem bomba
				g.drawImage(bombImg, x * largura + xAux, y*altura + yAux, null);
			else if(jogo.getQuantidadeBombas(x, y) == 0) //Expande 
				 g.drawImage(vazio, x * largura + xAux, y*altura + yAux, null);
			else //Desenha a quantidade de bombas ao redor				
                switch(jogo.getQuantidadeBombas(x, y)){
                	case 1:
                		g.drawImage(numero1, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 2:
                        g.drawImage(numero2, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 3:
                        g.drawImage(numero3, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 4:
                        g.drawImage(numero4, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 5:
                        g.drawImage(numero5, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 6:
                        g.drawImage(numero6, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 7:
                         g.drawImage(numero7, x * largura + xAux, y*altura + yAux, null);
                        break;
                    case 8:
                        g.drawImage(numero8, x * largura + xAux, y*altura + yAux, null);
                        break; 
                    default:
        				g.drawImage(openedBox, x * largura + xAux, y*altura + yAux, null);

                 }
		}
	}
	
	public void localizacaoBotao(Graphics g, Jogo jogo, int altura, int largura) { //De acordo com a posicao do cursor ou das setas dá destaque a ela
		if(!jogo.getAtivacao(x, y)) 
		{
			if(jogo.getFlag(x, y)) g.drawImage(flagImgEncima, x * largura + xAux, y*altura + yAux, null);
			else g.drawImage(encima, x * largura + xAux, y*altura + yAux, null);
		}
	}
}

