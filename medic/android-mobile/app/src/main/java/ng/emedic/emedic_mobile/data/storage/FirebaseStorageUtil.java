package ng.emedic.emedic_mobile.data.storage;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Locale;

import ng.emedic.emedic_mobile.callbacks.UploadCallback;
import ng.emedic.emedic_mobile.utils.EmedicUtils;

public class FirebaseStorageUtil {
    private static final String BASE_URL = "resources";
    private static final String ICON_PATH = "icons";
    private static final String IMAGE_PATH = "images";
    private static final String SERVICES = "services";
    private static final String PROFILE = "profile";
    private StorageReference storageInstance;
    private static UploadTask uploadTask;
    private FirebaseStorageUtil() {

    }

    public StorageReference getStorageInstance() {
        if (storageInstance == null) {
            storageInstance = FirebaseStorage.getInstance().getReference();
        }
        return storageInstance;
    }

    private String getServicesIconPath() {
        return String.format("%s/%s/%s/", BASE_URL, ICON_PATH, SERVICES);
    }

    private String getProfileImagePath() {
        return String.format("%s/%s/%s/", BASE_URL, IMAGE_PATH, PROFILE);
    }

    public static void uploadProfileImage(@NonNull String userName, final Uri uri, final Context context, NumberProgressBar... progresses) {
        String fileExtension = EmedicUtils.getFileExtension(uri, context);
        FirebaseStorageUtil storageUtil = new FirebaseStorageUtil();
        StorageReference ref = storageUtil.getStorageInstance().child(storageUtil.getProfileImagePath() +
                userName.toLowerCase() + fileExtension);
        final NumberProgressBar progressBar;
        if (progresses.length > 0) {
            progressBar = progresses[0];
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar = null;
        }
        uploadTask = ref.putFile(uri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                if (context instanceof UploadCallback) {
//                    Uri downloadUri = taskSnapshot.getDownloadUrl();
//                    if (downloadUri != null) {
//                        UploadCallback uploadCallback = (UploadCallback) context;
//                        uploadCallback.updateProgress(100);
//                        uploadCallback.onUploaded(downloadUri.toString());
//                    }
//                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (context instanceof UploadCallback) {
                    ((UploadCallback) context).onUploaded(null);
                }
            }
        })
        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                if (progressBar != null && context instanceof UploadCallback) {
                    final double progress = (100.0 * taskSnapshot.getBytesTransferred()) /
                            taskSnapshot.getTotalByteCount();
                    ((UploadCallback)context).updateProgress((int)progress);
                }
            }
        });
    }

    public static boolean cancelCurrentTask() {
        return uploadTask != null && uploadTask.isInProgress() && uploadTask.cancel();
    }

    public static boolean currentTaskIsCanceled() {
        return uploadTask != null && uploadTask.isCanceled();
    }
}
