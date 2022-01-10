import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static void saveGame(String savegameFile, GameProgress game) {
        try (FileOutputStream fos = new FileOutputStream(savegameFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    private static void deleteSave (String filepath) {
        File deletFile = new File(filepath);
        try {
            Deque<String> path = new ArrayDeque<>();
            String[] split = filepath.split("/");
            for (String s : split) {
                path.add(s);
            }
            if (deletFile.delete()) {
                System.out.println("Файл " + path.getLast() + " удален");
            } else {
                System.out.println("Файл " + path.getLast() + " не удален");
            }
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    private static void zipFiles(String zipArchivName, List<String> zipFilesName) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipArchivName))) {
            String fileZipName = "save";
            int i = 1;
            for (String filename : zipFilesName) {
                FileInputStream fis = new FileInputStream(filename);
                ZipEntry entry = new ZipEntry(fileZipName + i + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
                i += 1;
            }
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void main(String[] args) {
        GameProgress game1 = new GameProgress(100, 150, 10, 200.2);
        GameProgress game2 = new GameProgress(100, 200, 8, 150.2);
        GameProgress game3 = new GameProgress(100, 100, 5, 100.2);
        saveGame("C:/Users/weyma/Documents/Games/savegames/save1.dat", game1);
        saveGame("C:/Users/weyma/Documents/Games/savegames/save2.dat", game2);
        saveGame("C:/Users/weyma/Documents/Games/savegames/save3.dat", game3);
        List<String> zipFilesName = Arrays.asList(
                "C:/Users/weyma/Documents/Games/savegames/save1.dat",
                "C:/Users/weyma/Documents/Games/savegames/save2.dat",
                "C:/Users/weyma/Documents/Games/savegames/save3.dat"
        );
        zipFiles("C:/Users/weyma/Documents/Games/savegames/zip.zip", zipFilesName);

        deleteSave("C:/Users/weyma/Documents/Games/savegames/save1.dat");
        deleteSave("C:/Users/weyma/Documents/Games/savegames/save2.dat");
        deleteSave("C:/Users/weyma/Documents/Games/savegames/save3.dat");

    }
}
