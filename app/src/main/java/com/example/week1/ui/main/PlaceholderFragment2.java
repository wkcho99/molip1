package com.example.week1.ui.main;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.week1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.File;
import java.util.ArrayList;
import android.Manifest;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import com.example.week1.ui.main.MediaScanner;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import static android.os.Environment.DIRECTORY_PICTURES;

public class PlaceholderFragment2 extends Fragment {
    private ArrayList<String> mArrayList = new ArrayList<String>();
    private ImageAdapter mAdapter;
    private Context context;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ContentResolver contentResolver;
    public static PlaceholderFragment2 newInstance() {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }
    private static final int REQUEST_IMAGE_CAPTURE = 672;
    private String imageFilePath;
    private Uri photoUri;
    private com.example.week1.ui.main.MediaScanner mMediaScanner; // 사진 저장 시 갤러리 폴더에 바로 반영사항을 업데이트 시켜주려면 이 것이 필요하다(미디어 스캐닝)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        mArrayList = new ArrayList<>();
        mAdapter = new ImageAdapter(context, mArrayList);
        contentResolver = context.getContentResolver();
    }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_second, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.gallery);
        mRecyclerView.setHasFixedSize(true);
        // 사진 저장 후 미디어 스캐닝을 돌려줘야 갤러리에 반영됨.
        mMediaScanner = MediaScanner.getInstance(getActivity().getApplicationContext());


        root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent); // Code ADD
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                    }
                    if (photoFile != null) {
                        photoUri = FileProvider.getUriForFile(getActivity().getApplicationContext(), getActivity().getPackageName(), photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });
        int numberOfColumns = 3;
        mLayoutManager = new GridLayoutManager(context,numberOfColumns);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mArrayList = new ArrayList<>();
        mAdapter = new ImageAdapter(context,mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        updateData();
        final SwipeRefreshLayout pullToRefresh = root.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
                pullToRefresh.setRefreshing(false);
            }
        });
        return root;
    }

//    //Camera
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "TEST_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        imageFilePath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int exifOrientation;
            int exifDegree;
            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else {
                exifDegree = 0;
            }
            String result = "";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss", Locale.getDefault());
            Date curDate = new Date(System.currentTimeMillis());
            String filename = formatter.format(curDate);
            String strFolderName = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES) + File.separator + "HONGDROID" + File.separator;
            File file = new File(strFolderName);
            if (!file.exists())
                file.mkdirs();
            File f = new File(strFolderName + "/" + filename + ".png");
            result = f.getPath();
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result = "Save Error fOut";
            }
            // 비트맵 사진 폴더 경로에 저장
            rotate(bitmap, exifDegree).compress(Bitmap.CompressFormat.PNG, 70, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
                // 방금 저장된 사진을 갤러리 폴더 반영 및 최신화
                mMediaScanner.mediaScanning(strFolderName + "/" + filename + ".png");
            } catch (IOException e) {
                e.printStackTrace();
                result = "File close Error";
            }
            // 이미지 뷰에 비트맵을 set 하여 이미지 표현
            ((ImageView) getView().findViewById(R.id.iv_result)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }
    private int exifOrientationToDegress(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    private Bitmap rotate(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getActivity().getApplicationContext(), "권한이 허용됨",Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getActivity().getApplicationContext(), "권한이 거부됨",Toast.LENGTH_SHORT).show();
        }
    };
    /* Update mArrayList and notify the change to the adapter */
    private void updateData(){
        mArrayList.clear();
        String[] projection = {
                MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, "UPPER(" + MediaStore.Images.Media.DATE_TAKEN + ") ASC");
        if (cursor.moveToFirst()) {
            do {
                Uri ContentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                mArrayList.add(String.valueOf(ContentUri));
            } while (cursor.moveToNext());
        }
        cursor.close();
        /* Notify to the adapter */
        mAdapter.notifyDataSetChanged();
    }
}