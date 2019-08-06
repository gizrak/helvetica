package me.ted.code.council.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.ted.code.council.support.exception.CouncilException;
import me.ted.code.council.support.response.ResponseCode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ZipUtil {

    private static final int COMPRESSION_LEVEL = 8;
    private static final int BUFFER_SIZE = 1024 * 8;

    public static void zip(String sourcePath, String output) throws FileNotFoundException {
        File sourceFile = new File(sourcePath);

        // return if is not file or directory
        if (!sourceFile.isFile() && !sourceFile.isDirectory()) {
            throw new FileNotFoundException();
        }

        try (FileOutputStream fos = new FileOutputStream(output);
             BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);
             ZipOutputStream zos = new ZipOutputStream(bos)) {

            // set compression level (max: 9, default: 8)
            zos.setLevel(COMPRESSION_LEVEL);

            // make each zip file
            zipEntry(sourceFile, sourcePath, zos);

            zos.finish();
        } catch (IOException ioe) {
            throw new CouncilException(ResponseCode.INTERNAL_ERROR, ioe);
        }
    }

    public static String getExtFilePath(File zipFile, String extension) {
        String retStr = null;
        ZipEntry zentry = null;

        try (FileInputStream fis = new FileInputStream(zipFile); ZipInputStream zis = new ZipInputStream(fis)) {

            // unzip all entries
            while ((zentry = zis.getNextEntry()) != null) {
                String fileNameToUnzip = zentry.getName();

                if (fileNameToUnzip.lastIndexOf(extension) > 0) {
                    // String filePath = zipFile.getAbsolutePath(); //moved by
                    // junyong from 2 lines above.
                    // filePath = filePath.substring(0,
                    // filePath.lastIndexOf(".epub"));
                    // retStr = filePath + "/" + fileNameToUnzip.substring(0,
                    // fileNameToUnzip.lastIndexOf("/") + 1);
                    retStr = "/" + fileNameToUnzip.substring(0, fileNameToUnzip.lastIndexOf("/") + 1);
                    break; // added by junyong
                }
            }
        } catch (IOException ioe) {
            throw new CouncilException(ResponseCode.INTERNAL_ERROR, ioe);
        }

        return retStr;
    }

    public static byte[] unzipFirstFile(File zipFile) {
        byte[] fileBytes = null;
        ZipEntry zentry = null;

        try (FileInputStream fis = new FileInputStream(zipFile); ZipInputStream zis = new ZipInputStream(fis)) {

            // unzip all entries
            while ((zentry = zis.getNextEntry()) != null) {
                if (!zentry.isDirectory()) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    IOUtils.copy(zis, baos);
                    fileBytes = baos.toByteArray();
                    break;
                }
            }
        } catch (IOException ioe) {
            throw new CouncilException(ResponseCode.INTERNAL_ERROR, ioe);
        }

        return fileBytes;
    }

    public static byte[] unzipOneFile(File zipFile, String extension) {
        byte[] fileBytes = null;
        ZipEntry zentry = null;

        try (FileInputStream fis = new FileInputStream(zipFile); ZipInputStream zis = new ZipInputStream(fis)) {

            // unzip all entries
            while ((zentry = zis.getNextEntry()) != null) {
                String fileNameToUnzip = zentry.getName();

                if (fileNameToUnzip.lastIndexOf(extension) >= 0) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    IOUtils.copy(zis, baos);
                    fileBytes = baos.toByteArray();
                    break;
                }
            }
        } catch (IOException ioe) {
            throw new CouncilException(ResponseCode.INTERNAL_ERROR, ioe);
        }

        return fileBytes;
    }

    public static void unzipAll(File zipFile, File targetDir) {
        ZipEntry zentry = null;

        try (FileInputStream fis = new FileInputStream(zipFile); ZipInputStream zis = new ZipInputStream(fis)) {

            // if exists remove
            if (targetDir.exists()) {
                FileUtils.deleteDirectory(targetDir);
            }
            targetDir.mkdir();

            // unzip all entries
            while ((zentry = zis.getNextEntry()) != null) {
                String fileNameToUnzip = zentry.getName();

                File targetFile = new File(targetDir, fileNameToUnzip);

                // if directory
                if (zentry.isDirectory()) {
                    new File(targetFile.getAbsolutePath()).mkdir();
                } else {
                    // make parent dir
                    new File(targetFile.getParent()).mkdir();
                    unzipEntry(zis, targetFile);
                }
            }
        } catch (IOException ioe) {
            throw new CouncilException(ResponseCode.INTERNAL_ERROR, ioe);
        }
    }

    private static void zipEntry(File sourceFile, String sourcePath, ZipOutputStream zos)
            throws IOException {
        // if directory
        if (sourceFile.isDirectory()) {
            // return if .metadata
            if (".metadata".equalsIgnoreCase(sourceFile.getName())) {
                return;
            }

            // sourceFile 의 하위 파일 리스트
            File[] fileArray = sourceFile.listFiles();
            for (File file : Objects.requireNonNull(fileArray)) {
                // recursive call
                zipEntry(file, sourcePath, zos);
            }
        } else {
            BufferedInputStream bis;
            String sFilePath = sourceFile.getPath();
            String zipEntryName = sFilePath.substring(sourcePath.length() + 1);

            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            final ZipEntry zentry = new ZipEntry(zipEntryName);
            zentry.setTime(sourceFile.lastModified());
            zos.putNextEntry(zentry);

            byte[] buffer = new byte[BUFFER_SIZE];
            int cnt = 0;
            while ((cnt = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
                zos.write(buffer, 0, cnt);
            }
            zos.closeEntry();

            bis.close();
        }
    }

    private static File unzipEntry(ZipInputStream zis, File targetFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(targetFile);

        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while ((len = zis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();

        return targetFile;
    }
}
