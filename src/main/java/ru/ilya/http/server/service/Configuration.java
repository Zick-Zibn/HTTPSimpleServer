package ru.ilya.http.server.service;

import com.beust.jcommander.Parameter;

import java.io.FileNotFoundException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.beust.jcommander.*;

public class Configuration {
    @Parameter(names = {"-p", "--port"}, description = "Номер порта")
    public static int PORT;
    @Parameter(names = {"-f", "--folder"}, description = "Каталог файлов")
    public static String PATHFILESFOLDER;

    public Configuration(String[] args) {
        // TODO pars parameters to extract port and path
        JCommander.newBuilder().addObject(this).build().parse(args);
    }

//    public int getPort() {
//        return port;
//    }

    public boolean checkParameters() {
        try {
            if (Files.notExists(Paths.get(this.PATHFILESFOLDER)))
                throw new FileNotFoundException("Каталог не существует");
            if (PORT < 1 || PORT > 65535)
                throw new NumberFormatException(String.format("Значение порта %d недопустимо, используйте значение в диапазоне от 1 до 65535", PORT));
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }catch (NumberFormatException e) {
            System.out.println(e);
        }
        return false;
    }
}
