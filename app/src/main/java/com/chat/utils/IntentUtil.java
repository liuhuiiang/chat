package com.chat.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.List;

/**
 * 2018/7/30 11:04
 * instructions：常用intent工具
 * author:liuhuiliang  email:825378291@qq.com
 **/
public class IntentUtil {
    /**
     * 打开文件
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static Intent openFile(File file) {
        if (file == null || !file.exists())
            return null;
        /* 取得扩展名 */
        String end = file
                .getName()
                .substring(file.getName().lastIndexOf(".") + 1,
                        file.getName().length()).toLowerCase();
        String filePath = file.getAbsolutePath();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return getAudioFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            return getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return getApkFileIntent(filePath);
        } else if (end.equals("ppt")) {
            return getPptFileIntent(filePath);
        } else if (end.equals("xls")) {
            return getExcelFileIntent(filePath);
        } else if (end.equals("doc")) {
            return getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return getChmFileIntent(filePath);
        } else if (end.equals("txt")) {
            return getTextFileIntent(filePath, false);
        } else {
            return getAllIntent(filePath);
        }
    }

    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    // Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    // Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    // Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    // Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param) {

        Uri uri = Uri.parse(param).buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content").encodedPath(param).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    // Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    // Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    // Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    // Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    // Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    // Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(String param, boolean paramBoolean) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (paramBoolean) {
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        } else {
            Uri uri2 = Uri.fromFile(new File(param));
            intent.setDataAndType(uri2, "text/plain");
        }
        return intent;
    }

    // Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(String param) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * 调用浏览器
     *
     * @param url
     * @return
     */
    public static Intent openUrl(String url) {
        Uri webViewUri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webViewUri);
        return intent;
    }

    /**
     * 调用地图
     *
     * @param longitude 经度
     * @param latitude  维度
     * @return
     */
    public static Intent location(float longitude, float latitude) {
        Uri mapUri = Uri.parse("geo:" + longitude + "," + latitude);
        Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
        return intent;
    }

    /**
     * 播放mp3
     *
     * @param file
     * @return
     */
    public static Intent openAudio(String file, String end) {
        Uri playUri = Uri.parse(file);
        Intent intent = new Intent(Intent.ACTION_VIEW, playUri);
        intent.setDataAndType(playUri, "audio/" + end);
        return intent;
    }

    /**
     * 播放视频 xh 2017-2-15 上午9:49:56
     *
     * @param file
     * @param end
     * @return
     */
    public static Intent openVideo(String file, String end) {
        Uri playUri = Uri.parse(file);
        Intent intent = new Intent(Intent.ACTION_VIEW, playUri);
        intent.setDataAndType(playUri, "video/" + end);
        return intent;
    }


    /**
     * 直接拨打电话，需要加上权限<uses-permission id="android.permission.CALL_PHONE" />
     *
     * @param phonNum
     * @return
     */
    public static Intent callPhone(int phonNum) {
        Uri callUri = Uri.parse("tel:" + phonNum);
        Intent intent = new Intent(Intent.ACTION_CALL, callUri);
        return intent;
    }

    /**
     * 调用发邮件（这里要事先配置好的系统Email，否则是调不出发邮件界面的）
     *
     * @param email
     * @return
     */
    public static Intent openEmail(String email) {
        Uri emailUri = Uri.parse(email);
        Intent intent = new Intent(Intent.ACTION_SENDTO, emailUri);
        return intent;
    }

    /**
     * 直接发邮件
     *
     * @param tos
     * @param ccs
     * @param text
     * @return
     */
    public static Intent sendEmail(String[] tos, String ccs, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, tos);
        intent.putExtra(Intent.EXTRA_CC, ccs);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.setType("text/plain");
        Intent.createChooser(intent, "Choose Email Client");
        return intent;
    }

    /**
     * 发短信
     *
     * @param text
     * @return
     */
    public static Intent openSms(String text) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra("sms_body", text);
        intent.setType("vnd.android-dir/mms-sms");
        return intent;
    }

    /**
     * 直接发短信
     *
     * @param phoneNum
     * @param text
     * @return
     */
    public static Intent sendSms(int phoneNum, String text) {
        Uri smsToUri = Uri.parse("smsto:" + phoneNum);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", text);
        return intent;
    }

    /**
     * 发彩信
     *
     * @param text
     * @return
     */
    public static Intent openMms(String text) {
        Uri mmsUri = Uri.parse("content://media/external/images/media/23");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra("sms_body", text);
        intent.putExtra(Intent.EXTRA_STREAM, mmsUri);
        intent.setType("image/png");
        return intent;
    }

    /**
     * 卸载应用
     *
     * @return
     */
    public static Intent uninstall(String packageName) {
        Uri uninstallUri = Uri.fromParts("package", packageName, null);
        Intent intent = new Intent(Intent.ACTION_DELETE, uninstallUri);
        return intent;
    }

    public static Intent install(File file, Context context, String authority) {
        // 安装应用
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, authority, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }

        return intent;
    }

    /**
     * 运行app
     *
     * @param context
     * @param package_name
     * @return
     */
    public static Intent run(Context context, String package_name) {
        return context.getPackageManager().getLaunchIntentForPackage(
                package_name);
    }

    /**
     * 是否有应用能处理这个intent
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean isSafeToHandleThisIntent(Context context,
                                                   Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(
                intent, 0);
        return activities.size() > 0;
    }

    public static Intent openCamera(@NonNull Context context, @NonNull File file, String authority) {
        File parentFile = file.getParentFile();
        if (!parentFile.exists())
            parentFile.mkdirs();
        Uri imageUri = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(context, authority, file);//通过FileProvider创建一个content类型的Uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        } else
            imageUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        return intent;
    }

    public static Intent screenshots(@NonNull Context context, @NonNull File imageFile, String authorityImage, @NonNull File saveFile, String authoritySave, int aspectX, int aspectY, int outputX,
                                     int outputY) {
        File parent = saveFile.getParentFile();
        if (!parent.exists())
            parent.mkdirs();
        Uri imageUri = null;
        Uri saveUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(context, authorityImage, imageFile);
            saveUri = FileProvider.getUriForFile(context, authoritySave, saveFile);
        } else {
            imageUri = Uri.fromFile(imageFile);
            saveUri = Uri.fromFile(saveFile);
        }
        return screenshots(context, imageUri, saveUri, aspectX, aspectY, outputX, outputY);
    }

    public static Intent screenshots(@NonNull Context context, @NonNull Uri imageUri, @NonNull Uri saveUri, int aspectX, int aspectY, int outputX,
                                     int outputY) {
        Intent intent1 = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent1.setDataAndType(imageUri, "image/*");
        intent1.putExtra("crop", "true");
        intent1.putExtra("aspectX", aspectX);
        intent1.putExtra("aspectY", aspectY);
        intent1.putExtra("outputX", outputX);
        intent1.putExtra("outputY", outputY);
        intent1.putExtra("scale", true);
        // intent1.putExtra("scale", true);//黑边
        intent1.putExtra("scaleUpIfNeeded", true);// 黑边
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        intent1.putExtra("return-data", false);
        intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent1.putExtra("noFaceDetection", true); // no face detection
        return intent1;
    }

    public static Intent photo2PhotoAlbum() {
        Intent pickIt = new Intent(Intent.ACTION_PICK, null);
        pickIt.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        return pickIt;
    }
}
