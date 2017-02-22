package com.drivy.android.blog.fileprovider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    private static final String SHARED_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".myfileprovider";
    private static final String SHARED_FOLDER = "shared";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void share(final View view) throws IOException {
        // Create a random image and save it in private app folder
        final File sharedFile = createFile();

        // Get the shared file's Uri
        final Uri uri = FileProvider.getUriForFile(this, SHARED_PROVIDER_AUTHORITY, sharedFile);

        // Create a intent
        final ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(this)
                .setType("image/*")
                .addStream(uri);

        // Start the intent
        final Intent chooserIntent = intentBuilder.createChooserIntent();
        startActivity(chooserIntent);
    }

    @NonNull
    private File createFile() throws IOException {
        final RandomBitmapFactory bitmapFactory = new RandomBitmapFactory();
        final Bitmap randomBitmap = bitmapFactory.createRandomBitmap();

        final File sharedFolder = new File(getFilesDir(), SHARED_FOLDER);
        sharedFolder.mkdirs();

        final File sharedFile = File.createTempFile("picture", ".png", sharedFolder);
        sharedFile.createNewFile();

        writeBitmap(sharedFile, randomBitmap);
        return sharedFile;
    }

    private static void writeBitmap(final File destination,
                                    final Bitmap bitmap) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(destination);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(outputStream);
        }
    }

    private static void close(final Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
        } catch (IOException ignored) {
        }
    }
}
