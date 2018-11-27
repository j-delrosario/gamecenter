package fall2018.csc2017.slidingtiles.SlidingTiles;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * An class for selecting the image.
 */
public class AndroidSelectImage extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();

            Bitmap bitmap;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
                GameActivity.setBackground(bitmap);
            } catch (Exception e) {
                Log.e("Exception", e.getStackTrace().toString());
            }
        }
        this.onBackPressed();
    }
}
