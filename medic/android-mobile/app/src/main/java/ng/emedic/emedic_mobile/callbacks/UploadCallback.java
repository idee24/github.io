package ng.emedic.emedic_mobile.callbacks;

public interface UploadCallback {
    void onUploaded(String url);
    void updateProgress(int progress);
}
