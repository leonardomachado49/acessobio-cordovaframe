package io.acesso.facecapture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
  private final String TAG = "CustomSurfaceView";
  private Bitmap mRounderBitmap;
  private Paint mXferPaint;
  private int drawableFace;

  CustomSurfaceView(Context context){
    super(context);
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);

    if (mRounderBitmap == null)
      mRounderBitmap = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(mRounderBitmap);

    if (mXferPaint == null)
      mXferPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    int pictureWidth = 1280;
    int pictureHeight = 720;
    Point centerOfCanvas = new Point(getWidth() / 2, getHeight() / 2);

    float rectW = 0;
    float rectH = 0;
    float left = 0;
    float top = 0;
    float right = 0;
    float bottom = 0;

    /*if (pictureWidth == 1280 && pictureHeight == 720) {
      rectW = (pictureWidth / (2.0F / (canvas.getHeight() / 1280F)));
      rectH = (pictureHeight / (0.8F  /(canvas.getWidth() / 720F)));

      left = centerOfCanvas.x - (rectW / 2);
      top = centerOfCanvas.y - (rectH / 2);
      right = centerOfCanvas.x + (rectW / 2);
      bottom = centerOfCanvas.y + (rectH / 2);
    }*/
	
	float currentScreenRatio = (float)canvas.getHeight() / (float)canvas.getWidth();
	currentScreenRatio = Math.round(currentScreenRatio * 100F) / 100F;
	float regularScreenRatio = (16F / 9F);
	regularScreenRatio = Math.round(regularScreenRatio * 100F) / 100F;

	if (currentScreenRatio == regularScreenRatio) {
		rectW = (pictureWidth / (2.0F / (canvas.getHeight() / 1280F)));
		rectH = (pictureHeight / (0.8F /(canvas.getWidth() / 720F)));
	} else {
		rectW = (pictureWidth / (2.0F / (canvas.getHeight() / 1280F)));
		rectH = (pictureHeight / (1.0F / (canvas.getWidth() / 720F)));
	}

	left = centerOfCanvas.x - (rectW / 2);
	top = centerOfCanvas.y - (rectH / 2);
	right = centerOfCanvas.x + (rectW / 2);
	bottom = centerOfCanvas.y + (rectH / 2);

    // Desenha o faceframe.png no canvas
    Drawable drawable = getResources().getDrawable(this.drawableFace);
    drawable.setBounds((int) left, (int) top, (int) right, (int) bottom);
    drawable.draw(canvas);

    canvas.drawBitmap(mRounderBitmap, 0, 0, mXferPaint);
  }

  public int getDrawableFace() {
    return drawableFace;
  }

  public void setDrawableFace(int drawableFace) {
    this.drawableFace = drawableFace;
  }
}
