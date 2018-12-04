import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

public class Userintfc extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//Variaveis responsaveis por dimensionar as imagens
	final int altura  = 635;
    final int largura = 545;
    final int dimAlt  = 9;
    final int dimLarg = 9;
    final int dimAltTela = 448/9;
    final int dimLargTela= 463/9;
    public final int pixelX = 42;
    public final int pixelY = 40;

    //Variaveis responsavel pelo jogo
    final int qntBomba = 10;
    public int mouseX;
    public int mouseY;
    public int buttonPress = -1;
    
    //Flags responsavel pelo jogo
    public boolean dentroJanela = true; //Se o mouse esta dentro da janela
    public boolean vitoria = false;
    public boolean derrota = false; 
    public boolean movimentar = false; //Se o mouse ou as setas estão movimentando
    public boolean okPressed = true; //Se o botao de Ok foi pressionado
    
    //Matrizes responsaveis por formar o mapa do jogo
    public AuxiliarTamanho [][] posicoes;  //Armazena as coordenadas min(x,y) e max(x,y)
	private Tabuleiro[][] tabuleiro; // Tabuleiro do jogo
	private Jogo jogo; //Variavel responsavel pela parte logica
	
	//Carregamento de imagens
    private BufferedImage feliz = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/feliz.png"),pixelX+10, pixelY+10);
	private BufferedImage triste = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/triste.png"), pixelX+10, pixelY+10);
	private BufferedImage pause = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/Pause.png"), largura, altura);
	private BufferedImage fundo = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/fundo.jpg"), largura, altura);
	private BufferedImage fundoCont = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/fundoCont.jpg"), 150, dimAltTela+28);
	//private BufferedImage vitoriaImg = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/vitoria.jpg"), largura, altura); //Descontinuado, porem pode ativar e retirar o comentario da linha 97

    public Userintfc(){
    	//Mouse(ou setas) iniciam na posicao (0,0)
    	mouseX = 0;
    	mouseY = 0;
    	//Inicializa as matrizes e o jogo
    	tabuleiro = new Tabuleiro [dimAlt][dimLarg];
    	posicoes = new AuxiliarTamanho [dimAlt][dimLarg];
    	jogo = new Jogo(dimAlt,dimLarg);
    	jogo.preProcessamento(dimAlt, dimLarg, qntBomba);
    	
    	for(int x = 0;x < dimLarg;x++) //Gera as matrizes
		{
			for(int y = 0;y < dimAlt;y++)
			{
				tabuleiro[x] [y] = new Tabuleiro(x, y, dimLargTela,dimAltTela);
				posicoes[x][y] = new AuxiliarTamanho(x,y);
			}
		}
    	
    	//Inicialização da janela de jogo
        this.setTitle("Campo minado");
        this.setSize(largura+3,altura+30);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null); //centralizando a janela
        
        //Instanciamento de variaveis e relacionamento delas com a classe Userintfc
        Janela janela = new Janela(); 
        this.setContentPane(janela);
        Teclado teclado = new Teclado(); 
        this.addKeyListener(teclado);
        Clicar click = new Clicar();
        this.addMouseListener(click);
        Mover mover = new Mover();
        this.addMouseMotionListener(mover);
    };
    
    //Classe responsavel por gerar as imagens do jogo
    public class Janela extends JPanel {
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){ //Função para atualizar as imagens
			if(!dentroJanela)
        		g.drawImage(pause, 0, 0,null); //Mouse localizado do lado de fora da janela (Tela panico)
        	else if(vitoria) {
        		//g.drawImage(vitoriaImg, 0, 0, null); //Tela de Vitoria
        		jogo.aberturaGeral(dimLarg,dimAlt); //Abertura da tabela
        		draw(g);
        		continuar();
        	}else if(derrota){ 
        		jogo.aberturaGeral(dimLarg,dimAlt); //Abertura da tabela
        		draw(g);
        	}else draw(g); //Tela normal
        }
    
	    public void draw(Graphics g){
	    	g.drawImage(fundo, 0, 0, null); //Imagem de fundo
	    	g.drawImage(fundoCont, 65, 40, null); //Imagem de fundo do contador
	    	for(int x = 0;x < dimLarg;x++) //Geração da imagem da tabela
			{
				for(int y = 0;y < dimAlt;y++){
					tabuleiro[x][y].draw(g, jogo, dimAltTela, dimLargTela, (vitoria | derrota));
				}
			}
	    	
	    	if (movimentar) { //Verifica se o mouse ou teclado esta movendo, caso positivo, atualiza a imagem do botao para destaque
	    		if(!(mouseX > dimAlt || mouseY > dimLarg || mouseX < 0 || mouseY < 0)) //Evita exception java.lang.ArrayIndexOutOfBoundsException
	    			tabuleiro[mouseX][mouseY].localizacaoBotao(g, jogo, dimAltTela, dimLargTela);
	    		movimentar = false;
	    	}
			if(!derrota) g.drawImage(feliz, 250, 50, null); //Seta a cara de perdedor
			else {
				g.drawImage(triste, 250, 50, null);
				if(okPressed){ //Inutiliza a tela inferior, caso tenha perdido, permitindo mexer nela só se apertar Ok
					
					//Gera a mensagem de derrota
					JButton Ok = new JButton("Você Perdeu");
					JPanel painel = new JPanel();
					JFrame wind = new JFrame("Perdeu");
						
					painel.add(Ok);
					wind.add(painel);
					wind.setLocationRelativeTo(null);
					wind.setResizable(false);
					wind.setVisible(true);
					wind.pack();
					wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					okPressed = false; //Inutiliza segunda tela
					Ok.addActionListener(new ActionListener() {
						  public void actionPerformed(ActionEvent e) {
							okPressed = true;
							wind.dispose();
						  }
						});		
				}
	    	}
		}
    }
    
    public void continuar() { //Gera a tela de vitoria
    	
    	if(okPressed) {//Inutiliza a tela vencido, caso tenha perdido, permitindo mexer nela só se apertar Ok
    		
    		//Tela de Vitoria
	    	JButton botaoCarregar = new JButton("Novo Jogo");
			JPanel painel = new JPanel();
			JFrame wind = new JFrame("Deseja Continuar");
			JButton botaoSair = new JButton("Sair");
			
			painel.add(botaoCarregar);
			painel.add(botaoSair);
			painel.setVisible(true);
			wind.setLocationRelativeTo(null);
			wind.setResizable(false);
			wind.add(painel);
			wind.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			wind.pack();
			wind.setVisible(true);
			okPressed = false;//Inutiliza segunda tela
			
			botaoCarregar.addActionListener(new ActionListener() { //Caso deseja jogar um novo jogo ele é reiniciado;
				  public void actionPerformed(ActionEvent e) {
					wind.dispose();
					reiniciar();

				  }
				});
			botaoSair.addActionListener(new ActionListener() { //Senao cancela o programa;
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
    	}
    }
    
    //Classe que pega as coordenadas x e y da tela do computador e transforma em coordenadas que podem ser usadas na matriz do jogo
    public class AuxiliarTamanho {
    	public int xLeft;
    	public int xRight;
    	public int yUp;
    	public int yDown;
    	
    	public AuxiliarTamanho(int x, int y) {
    		xLeft = x*dimLargTela + 40;
    		xRight = x*dimLargTela + 40 +dimLargTela;
    		yUp = y*(dimAltTela) + 180;
    		yDown = y*(dimAltTela)+ dimAltTela + 180;
    	}
    	
    	public boolean search(int xAux, int yAux) { //Busca das coordenadas x e y do botao pressionado
    		if(xAux >= xLeft && xAux < xRight) {
    			if(yAux >= yUp && yAux < yDown) {
    				return true;
    			}
    		}
    		return false;
    	}
    }
    
    //Sobrecarga do KeyAdapter para permitir a jogatina pelo teclado
    public class Teclado extends KeyAdapter{
    	
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case 10: //Botao enter para abrir uma posicao
					buttonPress = 1;
					movimentacao(); //Permite o destaque da posicao atual
					break;
				case 32: //Botao para colocar uma bandeira
					buttonPress = 3;
					movimentacao(); //Permite o destaque da posicao atual
					break;
				case 37: //esquerda ( <- )
					if(mouseX!=0)
						mouseX--;
					else {
						if(mouseY==0) {
							mouseX = 8; mouseY = 8;
						}else {
							mouseX = 8; mouseY--;
						}
					}
					break;
				case 39: // Direita ( -> )
					if(mouseX!=8)
						mouseX++;
					else
						if(mouseY == 8) {
							mouseX = 0; mouseY = 0;
						}else {
							mouseX = 0; mouseY++;
						}
					break;
				case 38:  //Cima (seta para cima)
					if(mouseY!=0)
						mouseY--;
					else 
						mouseY = 8;
					break;
				case 40: //baixo (seta para baixo)
					if(mouseY!=8)
						mouseY++;
					else 
						mouseY=0;
					break;
				case 82: //R
					reiniciar();
			    	break;
			}
			if(e.getKeyCode() != 82 && e.getKeyCode() != 10 && e.getKeyCode()!= 32 ) movimentar = true; //Se estiver so movimentando no painel
			repaint();
		}

    }
    
    //Funcao que busca as posicoes de x e y atuais
    public void buscaPosicao(int x, int y) {
    	for(int i=0; i<dimLarg; i++) 
			for(int j=0; j<dimAlt; j++) {
				if(posicoes[i][j].search(x, y)) {
					mouseX = i;
					mouseY = j;
					
				}
			}
    }
    
    //Classe responsavel por destacar os blocos onde o mouse esta 
    public class Mover extends MouseMotionAdapter{

		@Override
		public void mouseMoved(MouseEvent e) {
			if(!derrota && !vitoria) {
				buscaPosicao(e.getX(), e.getY());
				movimentar = true;
				repaint();
			}
				
		}
    	
    }

    public class Clicar extends MouseAdapter{

		public void mouseClicked(MouseEvent e) { //Quando o mouse e clicado
			mousePressed(e);
		}

		public void mouseExited(MouseEvent e) { //Quando estava dentro e sai da janela
			if(!derrota && !vitoria) {
				dentroJanela = false;
				repaint();
			}
		}

		public void mousePressed(MouseEvent e) { //O botao é pressionado
			System.out.println(e.getX()+" "+e.getY());
			buttonPress = e.getButton();
				
			if(dentroJanela && okPressed){
				if(buttonPress == 1)  //Verifica se clicou na carinha
					if (e.getX() >= 260 && e.getX() < 310 && e.getY() >=85 && e.getY() < 135) {
						reiniciar();
					}
				if(e.getY() < 180) //Verifica se esta fora da parte do quadriculado
					mouseX=-1;
				else
					buscaPosicao(e.getX(), e.getY()); //Busca a posicao clicada
			}else//Desativar botao de panico
				if(e.getX()>=197 && e.getX()<372 && e.getY() >= 194 && e.getY() < 370) {
					dentroJanela = true;
					mouseX = -1;
				}
		}				

		public void mouseReleased(MouseEvent e) { //Quando o bota é solto
			System.out.println("( "+mouseX+" ,"+mouseY+" ) "+buttonPress+" <<<<");
			
			if(!movimentar && dentroJanela) //Verifica se nao esta movimentando e se esta dentro da janela
				movimentacao();
		}
    	
    }
    
    //Funcao Responsavel pela conexao com a parte logica do programa
    public void movimentacao() {
    	if(!derrota && !vitoria) {	
			if(mouseX>=0) {
				if(buttonPress == 1) derrota = jogo.play(mouseX,mouseY); //Faz uma jogada abrindo uma coordenada
				else if (buttonPress == 3 && !jogo.getAtivacao(mouseX, mouseY)) jogo.flag(mouseX, mouseY); //Coloca flag em uma coordenada
				movimentar = false;
			}else {
				mouseX = 0;
			}
			if(jogo.verificacao(dimLarg, dimAlt, qntBomba)) //verifica se ganhou
				vitoria = true;
			repaint();
		}
    }
    
    //Funcao para reiniciar o jogo
    public void reiniciar() {
    	jogo = new Jogo(dimAlt,dimLarg);
	    jogo.preProcessamento(dimAlt, dimLarg, qntBomba);
	    mouseX = 0;
	    mouseY = 0;
	    vitoria=false;
	    derrota=false;
	    buttonPress = -1;
	    okPressed = true;
		repaint();
    }
}


