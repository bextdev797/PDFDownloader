# Add any ProGuard configurations specific to this
# extension here.

-keep public class com.brandonang.pdfdownloader.PDFDownloader {
    public *;
 }
-keeppackagenames gnu.kawa**, gnu.expr**

-optimizationpasses 4
-allowaccessmodification
-mergeinterfacesaggressively

-repackageclasses 'com/brandonang/pdfdownloader/repack'
-flattenpackagehierarchy
-dontpreverify
