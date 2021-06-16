import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class Loader
{
    public static BufferedImage load(String path)
    {
        try
        {
            return ImageIO.read(Objects.requireNonNull(Loader.class.getClassLoader().getResourceAsStream(path)));
        } catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage scale(BufferedImage source, int width, int height)
    {
        BufferedImage scale = new BufferedImage(width, height, source.getType());
        Graphics g = scale.getGraphics();
        g.drawImage(source, 0, 0, width, height, null);
        return scale;
    }
}