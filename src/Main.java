import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args){
		//open image
		BufferedImage image;
		try {
			image = ImageIO.read(new File("TestImages/testImage4.jpg"));
		} catch (IOException e) {
			System.out.println("Could not open file");
			return;
		}

		//prepare image
		PixelMap pixM = new PixelMap(image);
		System.out.println("image size:" + pixM.getWidth() + "x" + pixM.getHeight());

		//convert image
		Converter con = new Converter(1,1900);

		//save string
		System.out.println(con.PixelMapToString(pixM,true));
	}
}