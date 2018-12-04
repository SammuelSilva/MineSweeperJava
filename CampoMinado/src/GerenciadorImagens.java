import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GerenciadorImagens {
    public static BufferedImage loadImage(String path)
    {
            try
            {
                return ImageIO.read(GerenciadorImagens.class.getClassLoader().getResourceAsStream(path));
            } catch(IOException e)
            {
                e.printStackTrace();
            }
            return null;
    }
    // Funcao scale: realiza o redimencionamento das imagens de acordo com as dimensões do tabuleiro
    public static BufferedImage scale(BufferedImage fonte, int largura, int altura)
    {
            BufferedImage scaled = new BufferedImage(largura, altura, fonte.getType());
            Graphics g = scaled.getGraphics();
            g.drawImage(fonte, 0, 0, largura, altura, null);
            g.dispose();
            return scaled;
    }
}
