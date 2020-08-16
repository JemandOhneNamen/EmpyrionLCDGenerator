import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class PixelMap {
	private final byte[] pixels;
	private final int width;
	private final int height;
	private final boolean hasAlpha;
	private final int pixelLength;

	private final static int threshold = 50;

	public PixelMap(BufferedImage input) {
		pixels = ((DataBufferByte) input.getRaster().getDataBuffer()).getData();
		width = input.getWidth();
		height = input.getHeight();
		hasAlpha = input.getAlphaRaster() != null;
		pixelLength = hasAlpha ? 4 : 3;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getPixelR(int w, int h){
		if(w >= width || h >= height)   //out of bounds
			return 0;
		return pixels[(width*h+w+1)*pixelLength-1]& 0xff;
	}

	public int getPixelG(int w, int h){
		if(w >= width || h >= height)   //out of bounds
			return 0;
		return pixels[(width*h+w+1)*pixelLength-2]& 0xff;
	}

	public int getPixelB(int w, int h){
		if(w >= width || h >= height)   //out of bounds
			return 0;
		return pixels[(width*h+w+1)*pixelLength-3]& 0xff;
	}

	public int getPixelA(int w, int h){
		if(w >= width || h >= height || !hasAlpha)   //out of bounds or no alphaChannel
			return 0;
		return pixels[(width*h+w)*pixelLength]& 0xff;
	}

	public boolean isPixWhite(int w, int h){
		if(w >= width || h >= height)   //out of bounds
			return false;
		return getPixelR(w,h)+getPixelG(w,h)+getPixelB(w,h) > threshold*3;
	}

	public boolean isPixBlack(int w, int h){
		if(w >= width || h >= height)   //out of bounds
			return false;
		return getPixelR(w,h)+getPixelG(w,h)+getPixelB(w,h) <= threshold*3;
	}

	public String printPixel(int w, int h){
		return null;
	}
}
