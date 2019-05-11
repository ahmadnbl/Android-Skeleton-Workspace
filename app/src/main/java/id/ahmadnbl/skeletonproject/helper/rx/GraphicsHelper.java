package id.ahmadnbl.skeletonproject.helper.rx;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.EmbossMaskFilter;
import android.graphics.Matrix;
import android.view.View;
import android.widget.TextView;

import androidx.exifinterface.media.ExifInterface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import id.ahmadnbl.skeletonproject.helper.FileHelper;

/**
 * Created by billy on 28/11/16.
 *
 */

public class GraphicsHelper {

    public static void setTextDebossEffect(TextView view){
        EmbossMaskFilter filter = new EmbossMaskFilter(
                new float[]{ 0f, -1f, 0.5f }, // direction of the light source
                0.8f, // ambient light between 0 to 1
                15f, // specular highlights
                1.0f // blur before applying lighting
        );
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        view.getPaint().setMaskFilter(filter);
    }


    /**
     * Do image compressing up to 320px and compressing up to 70 percent quality
     * @param filePath the target file need to be processed
     */
    public static void resizeImage(String filePath) {
        final int REQUIRED_SIZE = 320;
        // Setting option to resize image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // Load file with option parameter, then compress it
        Bitmap thumbnail = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

        // Write to file
        try {
            FileHelper.writeByteToFile(bytes.toByteArray(), filePath);
            thumbnail.recycle();
            bytes.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Do image compressing up to 320px and compressing up to 70 percent quality
     * @param bitmap the target bitmap need to be processed
     * @return resized {@link Bitmap}
     */
    public static Bitmap resizeBitmap(Bitmap bitmap) {

        // getting bitmap in byte array
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();

        final int REQUIRED_SIZE = 320;
        // Setting option to resize image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);

        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        // Load file with option parameter, then compress it
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
    }

    /**
     * Rotate the given image file to match with EXIF rotation data
     * @param filepath target image file
     */
    public static void rotateToExactRotaion(String filepath){
        boolean isChanged = false;
        try {
            ExifInterface ei = new ExifInterface(filepath);
            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    isChanged = true;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    isChanged = true;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    isChanged = true;
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    break;
            }
            if(isChanged){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileHelper.writeByteToFile(bytes.toByteArray(), filepath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rotate the given bitmap to match given angle
     * @param source given bitmap to rotate
     * @param angle angle target
     * @return rotated bitmap
     */
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}
