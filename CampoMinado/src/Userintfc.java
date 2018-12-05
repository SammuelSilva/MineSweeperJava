import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class Userintfc extends JFrame {
	final int altura  = 635; //9
    final int largura = 545; // 7
    final int dimAlt  = 9;
    final int dimLarg = 9;
    final int dimAltTela = 448/9;
    final int dimLargTela= 463/9;
    public final int pixelX = 42;
    public final int pixelY = 40;
    
    final int qntBomba=10;
    final int espaco  = 5;
    public int mouseX;
    public int mouseY;
    public boolean dentroJanela = true;
    public int buttonPress = -1;
    public boolean vitoria = false;
    public boolean derrota = false;
    public boolean movimentar = false;
   
    public AuxiliarTamanho [][] posicoes;
	private Tabuleiro[] [] tabuleiro; // Tabuleiro do jogo
	private Jogo jogo;
	
    private BufferedImage feliz = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/feliz.png"),pixelX+10, pixelY+10);
	private BufferedImage triste = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/triste.png"), pixelX+10, pixelY+10);
	private BufferedImage pause = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/Pause.png"), largura, altura);
	private BufferedImage fundo = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/fundo.jpg"), largura, altura);
	private BufferedImage vitoriaImg = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/vitoria.jpg"), largura, altura);
	private BufferedImage fundoCont = GerenciadorImagens.scale(GerenciadorImagens.loadImage("imagens/fundoCont.jpg"), 150, dimAltTela+28);

    public Userintfc(){
    	mouseX = 0;
    	mouseY = 0;
    	tabuleiro = new Tabuleiro [dimAlt][dimLarg];
    	posicoes = new AuxiliarTamanho [dimAlt][dimLarg];
    	jogo = new Jogo(dimAlt,dimLarg);
    	jogo.preProcessamento(dimAlt, dimLarg, qntBomba);
    	
    	for(int x = 0;x < dimLarg;x++)
		{
			for(int y = 0;y < dimAlt;y++)
			{
				tabuleiro[x] [y] = new Tabuleiro(x, y, dimLargTela,dimAltTela);
				posicoes[x][y] = new AuxiliarTamanho(x,y+1);
			}
		}
    	
        this.setTitle("Campo minado");
        this.setSize(largura+3,altura+30);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        Janela janela = new Janela(); //Instancia uma janela
        this.setContentPane(janela); //Relaciona a Janela com a classe Userintfc
        Teclado teclado = new Teclado();
        this.addKeyListener(teclado);
        Clicar click = new Clicar();
        this.addMouseListener(click);
    };
    
    public class Janela extends JPanel {
  
		public void paintComponent(Graphics g){
			if(!dentroJanela)
        		g.drawImage(pause, 0, 0,null);
        	else if(vitoria) {
        		g.drawImage(vitoriaImg, 0, 0, null);
        		continuar();
        	}else if(derrota){ 
        		jogo.derrota(dimLarg,dimAlt);
        		draw(g);
        	}else draw(g);
        }
    
	    public void draw(Graphics g){
	    	g.drawImage(fundo, 0, 0, null);
	    	g.drawImage(fundoCont, 65, 40, null);
	    	for(int x = 0;x < dimLarg;x++)
			{
				for(int y = 0;y < dimAlt;y++){
					tabuleiro[x][y].draw(g, jogo, dimAltTela, dimLargTela);
				}
			}
	    	
	    	if (movimentar) {
	    		tabuleiro[mouseX][mouseY].localizacaoBotao(g, jogo, dimAltTela, dimLargTela);
	    		movimentar = false;
	    	}
	    	
			if(!derrota) g.drawImage(feliz, 250, 50, null); 
			else{
				{
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
					Ok.addActionListener(new ActionListener() {
						  public void actionPerformed(ActionEvent e) {
							wind.dispose();
						  }
						});
				}
				g.drawImage(triste, 250, 50, null);
			}
		}
    }
    
    public void continuar() {
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
		
		botaoCarregar.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				jogo = new Jogo(dimAlt,dimLarg);
			    jogo.preProcessamento(dimAlt, dimLarg, qntBomba);
			    mouseX = 0;
			    mouseY = 0;
			    vitoria=false;
			    buttonPress = -1;
				wind.dispose();
				repaint();
			  }
			});
		
		botaoSair.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e) {
		    System.exit(0);
		  }
		});
    }
    public class AuxiliarTamanho {
    	public int xLeft;
    	public int xRight;
    	public int yUp;
    	public int yDown;
    	
    	public AuxiliarTamanho(int x, int y) {
    		xLeft = x*dimLargTela + 40;
    		xRight = x*dimLargTela + 40 +dimLargTela;
    		yUp = y*(dimAltTela) + 146;
    		yDown = y*(dimAltTela)+ dimAltTela + 146;
    	}
    	
    	public boolean search(int xAux, int yAux) {
    		if(xAux >= xLeft && xAux < xRight) {
    			if(yAux >= yUp && yAux < yDown) {
    				return true;
    			}
    		}
    		return false;
    	}
    }
    
    public class Teclado implements KeyListener{
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case 10:
					buttonPress = 1;
					movimentacao();
					break;
				case 32:
					buttonPress = 3;
					movimentacao();
					break;
				case 37:
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
				case 39:
					if(mouseX!=8)
						mouseX++;
					else
						if(mouseY == 8) {
							mouseX = 0; mouseY = 0;
						}else {
							mouseX = 0; mouseY++;
						}
					break;
				case 38:
					if(mouseY!=0)
						mouseY--;
					else 
						mouseY = 8;
					break;
				case 40:
					if(mouseY!=8)
						mouseY++;
					else 
						mouseY=0;
					break;
				case 82:
					jogo = new Jogo(dimAlt,dimLarg);
			    	jogo.preProcessamento(dimAlt, dimLarg, qntBomba);
			    	derrota = false;
			    	mouseX = 0;
			    	mouseY = 0;
			    	break;
			}
			if(e.getKeyCode() != 82 && e.getKeyCode() != 10 && e.getKeyCode()!= 32 ) movimentar = true;
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			return;
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			return;
			
		}

    }

    public class Clicar implements MouseListener{

		public void mouseClicked(MouseEvent e) { //Quando o mouse e clicado
			mousePressed(e);
		}

		public void mouseEntered(MouseEvent e) { //Quando estava fora e entra na janela
			return;
		}

		public void mouseExited(MouseEvent e) { //Quando estava dentro e sai da janela
			if(!derrota && !vitoria) {
				dentroJanela = false;
				repaint();
			}
		}

		public void mousePressed(MouseEvent e) { //O botao é pressionado
				buttonPress = e.getButton();
				System.out.println(e.getX()+" "+e.getY());
				if(dentroJanela){
					if(buttonPress == 1 && e.getX() >= 260 && e.getX() < 310 && e.getY() >=85 && e.getY() < 135) {
						jogo = new Jogo(dimAlt,dimLarg);
				    	jogo.preProcessamento(dimAlt, dimLarg, qntBomba);
				    	derrota = false;
				    	mouseX = 0;
				    	mouseY = 0;
				    	movimentar = false;
				    	buttonPress = -1;
						return;
					}
					if(e.getY() < 192) mouseX=-1;
					else
						for(int i=0; i<dimLarg; i++) {
							for(int j=0; j<dimAlt; j++) {
								if(posicoes[i][j].search(e.getX(), e.getY())) {
									mouseX = i;
									mouseY = j;
									return;
								}
							}
						}
				}else{
					if(e.getX()>=197 && e.getX()<372 && e.getY() >= 194 && e.getY() < 370) {
						dentroJanela = true;
						mouseX = -1;
					}
				}
		}				

		public void mouseReleased(MouseEvent e) { //O botao é solto
			System.out.println("( "+mouseX+" ,"+mouseY+" ) "+buttonPress+" <<<<");
			if(!movimentar && dentroJanela)
				movimentacao();
		}
    	
    }
    
    public void movimentacao() {
    	if(!derrota && !vitoria) {	
			if(mouseX>=0) {
				if(buttonPress == 1) derrota = jogo.play(mouseX,mouseY);
				else if (buttonPress == 3 && !jogo.getAtivacao(mouseX, mouseY)) jogo.flag(mouseX, mouseY);
				movimentar = false;
			}else {
				mouseX = 0;
			}
			if(jogo.verificacao(dimLarg, dimAlt, qntBomba))
				vitoria = true;
			repaint();
		}
    }
}

