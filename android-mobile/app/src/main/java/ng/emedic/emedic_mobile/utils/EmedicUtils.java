package ng.emedic.emedic_mobile.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ng.emedic.emedic_mobile.R;
import ng.emedic.emedic_mobile.activities.ServicesActivity;
import ng.emedic.emedic_mobile.data.storage.FirebaseStorageUtil;
import ng.emedic.emedic_mobile.networking.models.User;

public class EmedicUtils {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

        return email.matches(emailPattern);
    }

    public static void  launchActivity(AppCompatActivity context,
                                       Class activityClass, Bundle bundle, boolean isNewTask) {
        Intent intent = new Intent(context, activityClass);
        if (bundle != null)
            intent.putExtras(bundle);
        if (isNewTask)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public static void setImageBitmap(@NonNull CircleImageView imageView, @NonNull Uri uri, Context context) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMimeType(Uri uri, Context context) {
        return context.getContentResolver().getType(uri);
    }

    public static String getFileName(Uri uri) {
        String fileName = uri.toString();
        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }

    public static long getFileSize(Uri uri, Context context) {
        long fileSize = 0;
        Cursor cursor = context.getContentResolver()
                .query(uri, null, null, null, null);
        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            cursor.moveToFirst();
            fileSize = cursor.getLong(sizeIndex);
            cursor.close();
        }

        return fileSize;
    }

    public static String getFileExtension(Uri uri, Context context) {
        String fileName = getFileName(uri);
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public static class EmedicImageChooser {
        private Uri uri;
        private CircleImageView imageView;
        private RelativeLayout imageViewHolder;
        private RelativeLayout progressBarHolder;
        private ScrollView profileContent;
        private NumberProgressBar progressBar;
        private String downloadUrl;
        private ImagePicker imagePicker;
        public EmedicImageChooser(final AppCompatActivity activity, final CircleImageView imageView,
                                  final RelativeLayout imageViewHolder) {
            this.imageView = imageView;
            this.imageViewHolder = imageViewHolder;
            imagePicker = new ImagePicker(activity, null, new OnImagePickedListener() {
                @Override
                public void onImagePicked(Uri imageUri) {
                    uri = imageUri;
                    System.out.println("URI: " + uri.toString());
                    if (uri == null) {
                        return;
                    }
                    if (EmedicUtils.getFileSize(uri, activity) > EmedicConstants.FILE_UPLOAD_LIMIT) {
                        Toast.makeText(activity, "Image Size Limit(1MB) Exceeded", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imageView.setImageURI(uri);
                    imageViewHolder.setVisibility(View.INVISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                    if (activity instanceof ServicesActivity) {
                        User user = ((ServicesActivity)activity).getUser();
                        if (user != null) {
                            progressBarHolder.setVisibility(View.VISIBLE);
                            profileContent.setVisibility(View.INVISIBLE);
                            FirebaseStorageUtil.uploadProfileImage(user.getUsername(), uri, activity, progressBar);
                        }
                    }
                }
            }).setWithImageCrop(1, 1);
        }

        public ImagePicker getImagePicker() {
            return imagePicker;
        }

        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public CircleImageView getImageView() {
            return imageView;
        }

        public void setImageView(CircleImageView imageView) {
            this.imageView = imageView;
        }

        public RelativeLayout getImageViewHolder() {
            return imageViewHolder;
        }

        public void setImageViewHolder(RelativeLayout imageViewHolder) {
            this.imageViewHolder = imageViewHolder;
        }

        public NumberProgressBar getProgressBar() {
            return progressBar;
        }

        public void setProgressBar(NumberProgressBar progressBar) {
            this.progressBar = progressBar;
        }

        public RelativeLayout getProgressBarHolder() {
            return progressBarHolder;
        }

        public void setProgressBarHolder(RelativeLayout progressBarHolder) {
            this.progressBarHolder = progressBarHolder;
        }

        public ScrollView getProfileContent() {
            return profileContent;
        }

        public void setProfileContent(ScrollView profileContent) {
            this.profileContent = profileContent;
        }

        public void showImageChooser() {
            imagePicker.choosePicture(true);
        }
    }

    public static void displayImage(final String imageUrl, final RelativeLayout holder, final RelativeLayout loader, final CircleImageView imageView) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            loader.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(imageUrl)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            loader.setVisibility(View.INVISIBLE);
                            holder.setVisibility(View.INVISIBLE);
                            imageView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            loader.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    public static void displayImage(final String imageUrl, final RelativeLayout loader,
                                    final CircleImageView imageView, final CircleImageView profileImageDefault) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            loader.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(imageUrl)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            loader.setVisibility(View.INVISIBLE);
                            profileImageDefault.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            e.printStackTrace();
                            loader.setVisibility(View.INVISIBLE);
                        }
                    });
        }
    }

    public static String get12Hour(int hourOfTheDay) {
        int hour;
        if (hourOfTheDay == 0 || hourOfTheDay ==  12) {
            hour = 12;
        } else if (hourOfTheDay < 12) {
            hour = hourOfTheDay;
        } else {
            hour = hourOfTheDay - 12;
        }
        return String.format(Locale.getDefault(), "%02d",hour);
    }

    public static int getHourOfTheDay(int hour, String meridian) {
        int hourOfTheDay;
        if (hour == 12 && meridian.equals("AM")) {
            hourOfTheDay = 0;
        } else if (meridian.equals("PM")) {
            hourOfTheDay = 12 + hour;
        } else {
            hourOfTheDay = hour;
        }
        return hourOfTheDay;
    }

    public static String getMeridian(int hourOfTheDay) {
        return (hourOfTheDay >= 0 && hourOfTheDay < 12) ? "AM" : "PM";
    }
}
