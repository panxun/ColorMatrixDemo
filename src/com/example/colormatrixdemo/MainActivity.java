package com.example.colormatrixdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @Desp:�Ѳ�ɫͼƬ���ܻ�ɫ����ͼƬ�䰵
 * @author xiechengfa2000@163.com
 * @date 2015-6-9 ����9:28:25
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// �Ѳ�ɫͼƬ���ɻ�ɫ
		// ����1��
		ImageView image1 = (ImageView) findViewById(R.id.imageView1);
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(0);
		ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		image1.setColorFilter(filter);

		// ����2��
		ImageView image2 = (ImageView) findViewById(R.id.imageView2);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.baby);
		image2.setImageBitmap(convertToBlackWhite(bmp));

		// ͼƬ�䰵
		// ����1
		ImageView image3 = (ImageView) findViewById(R.id.imageView3);
		Drawable drawable = getResources().getDrawable(R.drawable.mm);
		drawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		image3.setImageDrawable(drawable);

		// ����2��û�����ļ����image4��Ҫ��ʾ��ͼƬ��Ϊbackground,�ѱ䰵��ͼƬ����ɫ��Ϊsrc,�Ϳ���ʵ�ֱ䰵��Ч��
		// ImageView image4 = (ImageView) findViewById(R.id.imageView4);
	}

	/**
	 * ����ɫͼת��Ϊ���ڰ׶�ɫ
	 * 
	 * @param λͼ
	 * @return ����ת���õ�λͼ
	 */
	private Bitmap convertToBlackWhite(Bitmap bmp) {
		int width = bmp.getWidth(); // ��ȡλͼ�Ŀ�
		int height = bmp.getHeight(); // ��ȡλͼ�ĸ�
		int[] pixels = new int[width * height]; // ͨ��λͼ�Ĵ�С�������ص�����

		bmp.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				// ������ԭɫ
				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				// ת���ɻҶ�����
				grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}

		// �½�ͼƬ
		Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
		// ����ͼƬ����
		newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

		Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, 380, 460);
		return resizeBmp;
	}
}
