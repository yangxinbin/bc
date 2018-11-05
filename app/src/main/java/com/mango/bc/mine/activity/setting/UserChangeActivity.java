package com.mango.bc.mine.activity.setting;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.bc.R;
import com.mango.bc.base.BaseActivity;
import com.mango.bc.login.BunblePhoneActivity;
import com.mango.bc.login.UserDetailActivity;
import com.mango.bc.mine.activity.SettingActivity;
import com.mango.bc.mine.bean.UserBean;
import com.mango.bc.mine.jsonutil.MineJsonUtils;
import com.mango.bc.util.AppUtils;
import com.mango.bc.util.DateUtil;
import com.mango.bc.util.PhotoUtils;
import com.mango.bc.util.SPUtils;
import com.mango.bc.util.Urls;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserChangeActivity extends BaseActivity {
    @Bind(R.id.circleImageView)
    CircleImageView circleImageView;
    @Bind(R.id.textView_1)
    TextView textView1;
    @Bind(R.id.r1_e)
    ImageView r1E;
    @Bind(R.id.textView_2)
    TextView textView2;
    @Bind(R.id.r1_where)
    ImageView r1Where;
    @Bind(R.id.textView_3)
    TextView textView3;
    @Bind(R.id.r2_e)
    ImageView r2E;
    @Bind(R.id.textView_4)
    TextView textView4;
    @Bind(R.id.r3_e)
    ImageView r3E;
    @Bind(R.id.textView_5)
    TextView textView5;
    @Bind(R.id.r4_e)
    ImageView r4E;
    @Bind(R.id.textView_6)
    TextView textView6;
    @Bind(R.id.r5_e)
    ImageView r5E;
    @Bind(R.id.textView_7)
    TextView textView7;
    @Bind(R.id.r7_e)
    ImageView r7E;
    @Bind(R.id.textView_8)
    TextView textView8;
    @Bind(R.id.r8_e)
    ImageView r8E;
    @Bind(R.id.textView_9)
    TextView textView9;
    @Bind(R.id.r9_e)
    ImageView r9E;
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;
    private static final int OUTPUT_X = 480;
    private static final int OUTPUT_Y = 480;
    private String TAG = "UserChangeActivity";
    private SPUtils spUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change);
        spUtils = SPUtils.getInstance("bc", this);
        ButterKnife.bind(this);
        //申明对象
        getImageAndMes();//后台请求数据
        initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));

    }

    private void initView(UserBean userBean) {
        if (userBean == null)
            return;
        if (userBean.getAvator() != null) {
            Glide.with(this).load(Urls.HOST_GETFILE + "?name=" + userBean.getAvator().getFileName()).into(circleImageView);
        } else {
            circleImageView.setImageDrawable(getResources().getDrawable(R.drawable.head_pic2));
        }
        textView1.setText(userBean.getAlias());
        textView2.setText(userBean.getPhone());
        if (userBean.getUserProfile() != null) {
            StringBuffer s1 = new StringBuffer();
            StringBuffer s2 = new StringBuffer();
            for (int j = 0; j < userBean.getUserProfile().getIdentity().size(); j++) {
                s1.append(userBean.getUserProfile().getIdentity().get(j) + " ");
            }
            for (int i = 0; i < userBean.getUserProfile().getHobbies().size(); i++) {
                s2.append(userBean.getUserProfile().getHobbies().get(i) + " ");
            }
            textView3.setText(s1);
            textView4.setText(s2);
            textView5.setText(userBean.getUserProfile().getRealName());
            textView6.setText(userBean.getUserProfile().getAge());
            textView7.setText(userBean.getUserProfile().getSex());
            textView8.setText(userBean.getUserProfile().getCompany());
            textView9.setText(userBean.getUserProfile().getDuty());
            spUtils.put("identity",listToString(userBean.getUserProfile().getIdentity()));
            spUtils.put("hobbies",listToString(userBean.getUserProfile().getHobbies()));
        }

    }
    private String listToString(List<String> stringList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == stringList.size() - 1) {
                stringBuffer.append(stringList.get(i));
                break;
            }
            stringBuffer.append(stringList.get(i) + ",");
        }
        Log.v("uuuuuuuu", "----" + stringBuffer.toString());
        return stringBuffer.toString();
    }
/*    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void userMessageEventBus(UserMessageBean bean) {
        textView1.setText(bean.getResponseObject().getName());
        textView2.setText(bean.getResponseObject().getDepartment());
        textView3.setText(bean.getResponseObject().getPosition());
        textView4.setText(bean.getResponseObject().getUsername());
        textView5.setText(bean.getResponseObject().getEmail());
        //头像
        if (bean.getResponseObject().getAvator().getId() != null) {//
            Log.v("yxbb", "dddd");
            Glide.with(this).load(Urls.HOST + "/user-service/user/get/file?fileId=" + bean.getResponseObject().getAvator().getId()).into(circleImageView);
        }

    }*/

    private void getImageAndMes() {
    }


    private void saveImage() {
        // 假设已知头像文件的本地存储路径如下
    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                autoObtainStoragePermission();
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                autoObtainCameraPermission();
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    /**
     * 动态申请sdcard读写权限
     */
    private void autoObtainStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
            }
        } else {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        }
    }

    /**
     * 申请访问相机权限
     */
    private void autoObtainCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                    AppUtils.showToast(this, "您已经拒绝过一次");
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
            } else {//有权限直接调用系统相机拍照
                if (hasSdcard()) {
                    imageUri = Uri.fromFile(fileUri);
                    //通过FileProvider创建一个content类型的Uri
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        imageUri = FileProvider.getUriForFile(this, "com.mango.bc", fileUri);
                    }
                    PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                } else {
                    AppUtils.showToast(this, "设备没有SD卡！");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (hasSdcard()) {
                        imageUri = Uri.fromFile(fileUri);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //通过FileProvider创建一个content类型的Uri
                            imageUri = FileProvider.getUriForFile(this, "com.mango.bc", fileUri);
                        }
                        PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
                    } else {
                        AppUtils.showToast(this, "设备没有SD卡！");
                    }
                } else {
                    AppUtils.showToast(this, "请允许打开相机！！");
                }
                break;
            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
                } else {
                    AppUtils.showToast(this, "请允许打操作SDCard！！");
                }
                break;
            default:
        }
    }

    //存放图片的地址，可以改动
    private Uri BitMap(Bitmap bitmap) {
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/Bmob");    //保存地址及命名
        if (!tmpDir.exists()) {
            tmpDir.mkdir(); //生成目录进行保存
        }
        File img = new File(tmpDir.getAbsolutePath() + "avatar.png");
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);  //参:压缩的格式，图片质量85，输出流
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

/*    @OnClick({R.id.imageView_user_back, R.id.picture, R.id.r1, R.id.r2, R.id.r3, R.id.r4, R.id.r5})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_user_back:
                finish();
                break;
            case R.id.picture:
                showTypeDialog();
                break;
            case R.id.r1:
                intent = new Intent(this, NameActivity.class);
                intent.putExtra("name", textView1.getText());
                startActivity(intent);
                finish();
                break;
            case R.id.r2:
                intent = new Intent(this, CompanyActivity.class);//单位
                intent.putExtra("department", textView2.getText());
                startActivity(intent);
                finish();
                break;
            case R.id.r3:
                intent = new Intent(this, DepartmentActivity.class);//职称
                intent.putExtra("position", textView3.getText());
                startActivity(intent);
                finish();
                break;
            case R.id.r4:
                intent = new Intent(this, ChangePhoneActivity.class);//职称
                intent.putExtra("position", textView3.getText());
                startActivity(intent);
                break;
            case R.id.r5:
                intent = new Intent(this, ChangeEmailActivity.class);//职称
                intent.putExtra("position", textView3.getText());
                startActivity(intent);
                break;
            case R.id.where:
                showSeleteCity();
                break;
            *//*case R.id.r4:
                break;
            case R.id.r5:
                break;*//*
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode + "  resultCode:" + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            initView(MineJsonUtils.readUserBean(spUtils.getString("auth", "")));
            Log.v("jjjjjjjjjjj", "------");
        }
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "onActivityResult: resultCode!=RESULT_OK");
            return;
        }
        switch (requestCode) {
            //相机返回
            case CODE_CAMERA_REQUEST:
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                //upLoadMap(cropImageUri);
                break;
            //相册返回
            case CODE_GALLERY_REQUEST:
                if (hasSdcard()) {
                    //upLoadMap(cropImageUri);
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.mango.bc", new File(newUri.getPath()));
                    }
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, OUTPUT_X, OUTPUT_Y, CODE_RESULT_REQUEST);
                } else {
                    AppUtils.showToast(this, "设备没有SD卡！");
                }
                break;
            //裁剪返回
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, this);
                //upLoadMap(cropImageUri);
                //这里上传文件
                if (bitmap != null) {
                    showImages(bitmap);
                }
                break;
            default:
        }
    }

/*    private void upLoadMap(Uri uri) {
        Log.v("upLoadMap", uri.toString() + "-----upLoadMap-----" + sharedPreferences.getString("token", ""));
        HttpUtils.doPostWithAll(Urls.HOST_AVATAR, getRealFilePath(this, uri), sharedPreferences.getString("token", ""), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //listener.onFailure("MES_FAILURE",e);
                Log.v("upLoadMap", e.toString() + "-----MES_FAILURE-----" + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.showToast(getBaseContext(), "头像上传失败");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (String.valueOf(response.code()).startsWith("2")) {
                    //listener.onSuccess("MES_SUCCESS");//异步请求
                    //注册时以及获取Token这里不用重复获取了
                        *//*UserMessageBean bean = ProjectsJsonUtils.readJsonUserMessageBeans(response.body().string());//data是json字段获得data的值即对象
                        listener.getSuccessUserMessage(bean);*//*
                    UserMessageBean bean = ProjectsJsonUtils.readJsonUserMessageBeans(response.body().string());
                    EventBus.getDefault().postSticky(bean);
//                    Log.v("upLoadMap", "-----bean-----" + bean.getResponseObject().getAvator().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(getBaseContext(), "头像上传成功");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AppUtils.showToast(getBaseContext(), "头像上传失败");
                        }
                    });
                    Log.v("upLoadMap", response.body().string() + "******" + response.code() + Urls.HOST_AVATAR);
                    //listener.onSuccess("MES_FAILURE");
                }
            }
        });

    }*/

    /**
     * Try to return the absolute file path from the given Uri
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void showImages(Bitmap bitmap) {
        circleImageView.setImageBitmap(bitmap);
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rAll,R.id.imageView_user_back, R.id.circleImageView, R.id.r1, R.id.r2, R.id.r3, R.id.r4, R.id.r5, R.id.r6, R.id.r7, R.id.r8, R.id.r9})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageView_user_back:
                intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.circleImageView:
                //showTypeDialog();
                break;
            case R.id.r1:
                break;
            case R.id.r2:
                intent = new Intent(this, BunblePhoneActivity.class);
                intent.putExtra("phone", true);
                startActivityForResult(intent, 10);
                break;
            case R.id.r3:
                break;
            case R.id.r4:
                break;
            case R.id.r5:
                intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("name", textView5.getText());
                intent.putExtra("year", textView6.getText());
                intent.putExtra("sex", textView7.getText());
                intent.putExtra("company", textView8.getText());
                intent.putExtra("position", textView9.getText());
                startActivityForResult(intent, 10);
                break;
            case R.id.r6:
                intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("name", textView5.getText());
                intent.putExtra("year", textView6.getText());
                intent.putExtra("sex", textView7.getText());
                intent.putExtra("company", textView8.getText());
                intent.putExtra("position", textView9.getText());
                startActivityForResult(intent, 10);
                break;
            case R.id.r7:
                intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("name", textView5.getText());
                intent.putExtra("year", textView6.getText());
                intent.putExtra("sex", textView7.getText());
                intent.putExtra("company", textView8.getText());
                intent.putExtra("position", textView9.getText());
                startActivityForResult(intent, 10);
                break;
            case R.id.r8:
                intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("name", textView5.getText());
                intent.putExtra("year", textView6.getText());
                intent.putExtra("sex", textView7.getText());
                intent.putExtra("company", textView8.getText());
                intent.putExtra("position", textView9.getText());
                startActivityForResult(intent, 10);
                break;
            case R.id.r9:
                intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("name", textView5.getText());
                intent.putExtra("year", textView6.getText());
                intent.putExtra("sex", textView7.getText());
                intent.putExtra("company", textView8.getText());
                intent.putExtra("position", textView9.getText());
                startActivityForResult(intent, 10);
                break;
/*            case R.id.rAll:
                intent = new Intent(this, UserDetailActivity.class);
                intent.putExtra("update", true);
                intent.putExtra("name", textView5.getText());
                intent.putExtra("year", textView6.getText());
                intent.putExtra("sex", textView7.getText());
                intent.putExtra("company", textView8.getText());
                intent.putExtra("position", textView9.getText());
                startActivityForResult(intent, 10);
                break;*/
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent intent;
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
