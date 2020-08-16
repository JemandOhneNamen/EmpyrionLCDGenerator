public class Converter {
	private int LCD_LIMIT;
	private static int MAX_SYMBOLS;
	private static final int BIG_PIX_Size = 4;

	public Converter(int lcdLimit, int maxSymbols) {
		LCD_LIMIT = lcdLimit;
		MAX_SYMBOLS = maxSymbols;
	}

	public String PixelMapToString(PixelMap pixM, boolean BPixel) {
		if(BPixel)
			return PixelMapToStringComplex(pixM);
		else
			return PixelMapToStringSimple(pixM);
	}

	private String PixelMapToStringSimple(PixelMap pixM){
		if(pixM == null)
			return null;
		StringBuilder sb = new StringBuilder();
		sb.append("<size=6.2><color=#030303><mspace=4><line-height=4.6>");

		for (int h = 0; h < pixM.getHeight(); h++) {
			int spaces = 0;
			for (int w = 0; w < pixM.getWidth(); w++) {
				if(pixM.isPixBlack(w,h)){
					if(spaces > 8)
						sb.append("<pos=").append(w * 4).append('>');
					else
						sb.append(" ".repeat(spaces));
					spaces = 0;
					sb.append('■');
				}else
					spaces++;
			}
			sb.append('\n');
		}
		System.out.println(sb.length() + " characters");    //TODO
		return sb.toString();
	}

	private String PixelMapToStringComplex(PixelMap pixM){
		if(pixM == null)
			return null;
		StringBuilder sb = new StringBuilder();

		//finding and flagging 2x2 pixels
		boolean[][] bigPix = new boolean[pixM.getWidth()][pixM.getHeight()];
		for (int h = 0; h < pixM.getHeight(); h+=BIG_PIX_Size)
			for (int w = 0; w < pixM.getWidth(); w+=BIG_PIX_Size)
			    if(bigPixel(w,h,pixM))
			        setBigPix(w,h,bigPix);

        //draw 1x1 pixels
        sb.append("<size=6.4><color=#030303><mspace=4><line-height=4.6>");
		for (int h = 0; h < pixM.getHeight(); h++){
			int spaces = 0;
			for (int w = 0; w < pixM.getWidth(); w++){
				if(pixM.isPixBlack(w,h) && !bigPix[w][h]){
					if(spaces > 8)
						sb.append("<pos=").append(w * 4).append('>');
					else
						sb.append(" ".repeat(spaces));
					spaces = 0;
					sb.append('■');
				}else
					spaces++;
			}
			sb.append('\n');
		}

		//draw 2x2 pixels
        sb.append("<size=12.8><color=#030303><mspace=8><line-height=8.7>");
        for (int h = 0; h < pixM.getHeight(); h+=BIG_PIX_Size){
            int spaces = 0;
            for (int w = 0; w < pixM.getWidth(); w+=BIG_PIX_Size){
                if(pixM.isPixBlack(w,h) && bigPix[w][h]){
                    if(spaces > 8)
                        sb.append("<pos=").append(w * 4).append('>');
                    else
                        sb.append(" ".repeat(spaces));
                    spaces = 0;
                    sb.append('■');
                }else
                    spaces++;
            }
            sb.append('\n');
        }

		System.out.println(sb.length() + " characters");    //TODO
		return sb.toString();
	}

	//checks if all 4 pixels have the same color
	private boolean bigPixel(int w, int h, PixelMap pixM){
		for(int i = 0; i < BIG_PIX_Size; i++)
			for(int j = 0; j < BIG_PIX_Size; j++)
				if(!pixM.isPixBlack(w+i,h+j))
					return false;
		return true;
    }

    //flags all four pixels as big
    private boolean[][] setBigPix(int w, int h, boolean[][] bigPix) {
		for(int i = 0; i < BIG_PIX_Size; i++)
			for(int j = 0; j < BIG_PIX_Size; j++)
				bigPix[w+i][h+j] = true;
        return bigPix;
    }

    //scale <line-height>

}
