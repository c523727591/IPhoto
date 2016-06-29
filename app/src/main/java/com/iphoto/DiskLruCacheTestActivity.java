package com.iphoto;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import libcore.io.DiskLruCache;

/**
 * Created by duke on 16-6-29.
 */
// http://pic13.nipic.com/20110415/1369025_121513630398_2.jpg
// http://img.my.csdn.net/uploads/201407/26/1406382841_9821.jpg

public class DiskLruCacheTestActivity extends Activity {
    private DiskLruCache mDiskLruCache = null;
    private ImageView mImageView = null;
    private String mImageUrl = "http://pic13.nipic.com/20110415/1369025_121513630398_2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.disk_lru_cache_test_layout);

        mImageView = (ImageView)findViewById(R.id.disk_lru_cache_image_view_id);

        try {
            File directory = getDiskCacheDir(this, "dukeImage");
            Log.d("DiskLruCache", "directory = " + directory.getPath());

            if (!directory.exists()) {
                directory.mkdirs();
            }

            mDiskLruCache = DiskLruCache.open(directory, getAppVersion(this), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("DiskLruCache", "onCreate begin to download image.");
        BitmapWorkerTask task = new BitmapWorkerTask();
        task.execute(mImageUrl);
    }

    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        Log.d("DiskLruCache", "SDCard is valid = " + Environment.isExternalStorageEmulated());
        Log.d("DiskLruCache", "SDCard status is = " + Environment.getExternalStorageState());

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            // getCacheDir() 获取 /data/data/<application package>/cache目录
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            Log.d("DiskLruCache", "doInBackground");

            imageUrl = params[0];
            Log.d("DiskLruCache", "imageUrl = " + imageUrl);

            FileDescriptor fileDescriptor = null;
            FileInputStream fileInputStream = null;
            DiskLruCache.Snapshot snapShot = null;

            try {
                final String key = hashKeyForDisk(imageUrl);
                Log.d("DiskLruCache", "key = " + key);

                snapShot = mDiskLruCache.get(key);
                if (snapShot == null) {
                    Log.d("DiskLruCache", "don't find image in disk cache,download it.");

                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }

                    // 将记录同步，如果不同步，一直都是查不到
                    try {
                        mDiskLruCache.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    snapShot = mDiskLruCache.get(key);
                } else {
                    Log.d("DiskLruCache", "Yeah, find image in disk cache.");
                }

                if (snapShot != null) {
                    fileInputStream = (FileInputStream) snapShot.getInputStream(0);
                    fileDescriptor = fileInputStream.getFD();
                }

                Bitmap bitmap = null;
                if (fileDescriptor != null) {
                    bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                }

                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileDescriptor == null && fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d("DiskLruCache", "onPostExecute");
            super.onPostExecute(bitmap);

            if (mImageView != null && bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            }
        }

        private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
            HttpURLConnection urlConnection = null;
            BufferedOutputStream out = null;
            BufferedInputStream in = null;
            try {
                final URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
                out = new BufferedOutputStream(outputStream, 8 * 1024);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                return true;
            } catch (final IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
    }
}
