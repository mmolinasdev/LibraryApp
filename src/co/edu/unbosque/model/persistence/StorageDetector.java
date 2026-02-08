package co.edu.unbosque.model.persistence;

import java.io.File;

public class StorageDetector {
    
    public static String detectStorageLocation() {
        String envPath = System.getenv("LIBRARY_DATA_PATH");
        
        if (envPath != null && !envPath.isEmpty()) {
            return ensureTrailingSeparator(envPath);
        }
        
        String googleDrivePath = detectGoogleDrive();

        if (googleDrivePath != null) {
            return googleDrivePath;
        }
        
        String dropboxPath = detectDropbox();
        
        if (dropboxPath != null) {
            return dropboxPath;
        }
        
        return "data" + File.separator;
    }
    
    private static String detectGoogleDrive() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        
        if (os.contains("mac")) {
            return detectGoogleDriveMac(userHome);
        } else if (os.contains("win")) {
            return detectGoogleDriveWindows(userHome);
        }
        
        return null;
    }
    
    private static String detectGoogleDriveMac(String userHome) {
        File cloudStorage = new File(userHome + "/Library/CloudStorage");
        
        if (!cloudStorage.exists()) {
            return null;
        }
        
        File[] dirs = cloudStorage.listFiles((dir, name) -> name.startsWith("GoogleDrive-"));
        
        if (dirs == null || dirs.length == 0) {
            return null;
        }
        
        String[] possiblePaths = {
            dirs[0].getAbsolutePath() + "/Mi unidad/BD_1/LibraryManagementApp/data/",
            dirs[0].getAbsolutePath() + "/My Drive/BD_1/LibraryManagementApp/data/",
            dirs[0].getAbsolutePath() + "/Mi unidad/LibraryManagementApp/data/",
            dirs[0].getAbsolutePath() + "/My Drive/LibraryManagementApp/data/"
        };
        
        for (String path : possiblePaths) {
            if (new File(path).exists()) {
                return path;
            }
        }
        
        return null;
    }
    
    private static String detectGoogleDriveWindows(String userHome) {
        String[] basePaths = {
            userHome + "\\\\Google Drive",
            userHome + "\\\\GoogleDrive",
            "G:\\\\My Drive",
            "G:\\\\Mi unidad"
        };
        
        String[] subPaths = {
            "\\\\BD_1\\\\LibraryManagementApp\\\\data\\\\",
            "\\\\LibraryManagementApp\\\\data\\\\"
        };
        
        for (String basePath : basePaths) {
            for (String subPath : subPaths) {
                String fullPath = basePath + subPath;

                if (new File(fullPath).exists()) {
                    return fullPath;
                }
            }
        }
        
        String shortcutPath = detectGoogleDriveShortcuts();
        
        if (shortcutPath != null) {
            return shortcutPath;
        }
        
        File userHomeDir = new File(userHome);
        File[] userDirs = userHomeDir.listFiles();
        
        if (userDirs != null) {
            for (File dir : userDirs) {
                if (dir.isDirectory() && (dir.getName().contains("Google") || dir.getName().contains("Drive"))) {
                    for (String subPath : subPaths) {
                        String fullPath = dir.getAbsolutePath() + subPath;
                        
                        if (new File(fullPath).exists()) {
                            return fullPath;
                        }
                    }
                }
            }
        }
        
        return null;
    }
    
    private static String detectGoogleDriveShortcuts() {
        char[] possibleDrives = {'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
        
        String[] subPaths = {
            "\\\\BD_1\\\\LibraryManagementApp\\\\data\\\\",
            "\\\\LibraryManagementApp\\\\data\\\\"
        };
        
        for (char drive : possibleDrives) {
            String drivePath = drive + ":\\\\";
            File driveRoot = new File(drivePath);
            
            if (!driveRoot.exists()) {
                continue;
            }
            
            File shortcutDir = new File(drivePath + ".shortcut-targets-by-id");
            
            if (shortcutDir.exists() && shortcutDir.isDirectory()) {
                File[] folders = shortcutDir.listFiles();
                
                if (folders != null) {
                    for (File folder : folders) {
                        if (folder.isDirectory()) {
                            for (String subPath : subPaths) {
                                String fullPath = folder.getAbsolutePath() + subPath;

                                if (new File(fullPath).exists()) {
                                    return fullPath;
                                }
                            }
                        }
                    }
                }
            }
            
            for (String subPath : subPaths) {
                String fullPath = drivePath + subPath;
                
                if (new File(fullPath).exists()) {
                    return fullPath;
                }
            }
        }
        
        return null;
    }
    
    private static String detectDropbox() {
        String os = System.getProperty("os.name").toLowerCase();
        String userHome = System.getProperty("user.home");
        String path = null;
        
        if (os.contains("mac") || os.contains("linux")) {
            path = userHome + "/Dropbox/LibraryManagementApp/data/";
        } else if (os.contains("win")) {
            path = userHome + "\\Dropbox\\LibraryManagementApp\\data\\";
        }
        
        if (path != null && new File(path).exists()) {
            return path;
        }
        
        return null;
    }
    
    private static String ensureTrailingSeparator(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        
        String separator = File.separator;
        return path.endsWith(separator) ? path : path + separator;
    }
    
    public static String getStorageType(String dataDirectory) {
        String absolutePath = new File(dataDirectory).getAbsolutePath().toLowerCase();
        
        if (absolutePath.contains("googledrive") || 
            absolutePath.contains("google drive") || 
            absolutePath.contains("shortcut-targets-by-id")) {
            return "Google Drive";
        } else if (absolutePath.contains("dropbox")) {
            return "Dropbox";
        } else if (absolutePath.contains("onedrive")) {
            return "OneDrive";
        } else {
            return "Local";
        }
    }
}
