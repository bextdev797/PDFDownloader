package com.brandonang.pdfdownloader;

import java.io.File;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

public class PDFDownloader extends AndroidNonvisibleComponent {
    private Context context;
    private long lastID;
    private String downloadLink;
    private String pdfFileName;

    public PDFDownloader(ComponentContainer container) {
        super(container.$form());
        this.context = container.$context();
    }

    @SimpleFunction
    public void DownloadPDFFromGoogleDrive(String linkPDF, String fileName, long id) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(linkPDF);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
        lastID = downloadManager.enqueue(request);
        downloadLink = linkPDF;
        pdfFileName = fileName;
        DownloadPDFCompleted();
    }

    @SimpleFunction
    public void CancelPDFDownload() {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.remove(lastID);
        FailedPDFDownload();
    }

    @SimpleFunction
    public void ResumePDFDownload(long id, String fileName, String linkPDF) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        if (file.exists()) {
            DownloadPDFCompleted();
        } else {
            DownloadPDFFromGoogleDrive(linkPDF, fileName, id);
        }
    }

    @SimpleEvent
    public void FailedPDFDownload() {
        EventDispatcher.dispatchEvent(this, "FailedDownload");
    }

    @SimpleEvent
    public void DownloadPDFCompleted() {
        EventDispatcher.dispatchEvent(this, "DownloadPDFCompleted");
    }
}
