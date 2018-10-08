package ru.timokhin.Cloud.handler.keyboard;

import ru.timokhin.Cloud.api.local.FileLocalService;
import ru.timokhin.Cloud.api.local.FolderLocalService;
import ru.timokhin.Cloud.api.remote.FileRemoteService;
import ru.timokhin.Cloud.api.remote.FolderRemoteService;
import ru.timokhin.Cloud.event.keyboard.KeyboardCommandEvent;
import ru.timokhin.Cloud.system.AppService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.Scanner;

@ApplicationScoped
public class KeyboardCommandHandler {
    // Куски комманд:
    //для целых комманд:
    private static final String REMOTE = "remote";
    private static final String LOCAL = "local";
    private static final String FILE = "file";
    private static final String FOLDER = "folder";
    private static final String DELETE = "delete";
    private static final String READ = "read";
    private static final String EXISTS = "exists";
    private static final String LIST = "list";
    private static final String EXIT = "exit";
    private static final String HELP = "help";
    private static final String LOGIN = "login";
    private static final String LOGOUT = "logout";
    private static final String PREFIX = "-";
    private static final String ADD = "add";
    private static final String ABBREVIATIONS = "abbr";
    // для сокращенных комманд:
    private static final String REMOTE_SHRT = "rmt";
    private static final String LOCAL_SHRT = "lcl";
    private static final String FILE_SHRT = "fl";
    private static final String FOLDER_SHRT = "fd";
    private static final String DELETE_SHRT = "dlt";
    private static final String READ_SHRT = "rd";
    private static final String EXISTS_SHRT = "ext";
    private static final String LIST_SHRT = "lst";
    private static final String EXIT_SHRT = "ex";
    private static final String HELP_SHRT = "hlp";

//    //команды вызова списка команд и их описания
//    private static final String HELP_CMD = "help";
//    private static final String ABBREVIATIONS_CMD = "abbr";
//    // команда выхода из приложения
//    private static final String EXIT_CMD = "exit";
//    //команды входа/выхода из учетной записи
//    private static final String LOGIN_CMD = "login";
//    private static final String LOGOUT_CMD = "logout";
//    // команды поиска элементов по локальному и удаленному хранилищу
//    private static final String LOCAL_FILE_LIST_CMD = "local-file-list";
//    private static final String LOCAL_FILE_EXISTS_CMD = "local-file-exists";
//    private static final String LOCAL_FOLDER_LIST_CMD = "local-folder-list";
//    private static final String LOCAL_FOLDER_EXISTS_CMD = "local-folder-exists";
//    private static final String REMOTE_FILE_LIST_CMD = "remote-file-list";
//    private static final String REMOTE_FILE_EXISTS_CMD = "remote-file-exists";
//    private static final String REMOTE_FOLDER_LIST_CMD = "remote-folder-list";
//    private static final String REMOTE_FOLDER_EXISTS_CMD = "remote-folder-exists";
//    //команды изменения состояния папок/файлов в удаленом хранилище
//    private static final String REMOTE_FOLDER_ADD_CMD = "remote-folder-add";
//    private static final String REMOTE_FOLDER_DELETE_CMD = "remote-folder-delete";
//    private static final String REMOTE_FILE_DELETE_CMD = "remote-file-delete";
//    private static final String REMOTE_FILE_ADD_CMD = "remote-file-add";
//    //команды изменения состояния папок/файлов в локальном хранилище
//    private static final String LOCAL_FOLDER_ADD_CMD = "local-folder-add";
//    private static final String LOCAL_FOLDER_DELETE_CMD = "local-folder-delete";
//    private static final String LOCAL_FILE_DELETE_CMD = "local-file-delete";
//    private static final String LOCAL_FILE_ADD_CMD = "local-file-add";
//    //команды вывода содержимго файлов в консоль //todo: <- реализовать
//    private static final String LOCAL_FILE_READ_CMD = "local-file-read";
//    private static final String REMOTE_FILE_READ_CMD = "remote-file-read";


    // команды для тестов и отладки программы
    private static final String GET_REPO_CMD = "get-rep";
    private static final String GET_SESSION_CMD = "get-ses";
    //команды вызова списка команд и их описания
    private static final String HELP_CMD = HELP;
    private static final String ABBREVIATIONS_CMD = ABBREVIATIONS;
    // команда выхода из приложения
    private static final String EXIT_CMD = EXIT;
    //команды входа/выхода из учетной записи
    private static final String LOGIN_CMD = LOGIN;
    private static final String LOGOUT_CMD = LOGOUT;
    // команды поиска элементов по локальному и удаленному хранилищу
    private static final String LOCAL_FILE_LIST_CMD = LOCAL + PREFIX + FILE + PREFIX + LIST;
    private static final String LOCAL_FILE_EXISTS_CMD = LOCAL + PREFIX + FILE + PREFIX + EXISTS;
    private static final String LOCAL_FOLDER_LIST_CMD = LOCAL + PREFIX + FOLDER + PREFIX + LIST;
    private static final String LOCAL_FOLDER_EXISTS_CMD = LOCAL + PREFIX + FOLDER + PREFIX + EXISTS;
    private static final String REMOTE_FILE_LIST_CMD = REMOTE + PREFIX + FILE + PREFIX + LIST;
    private static final String REMOTE_FILE_EXISTS_CMD = REMOTE + PREFIX + FILE + PREFIX + EXISTS;
    private static final String REMOTE_FOLDER_LIST_CMD = REMOTE + PREFIX + FOLDER + PREFIX + LIST;
    private static final String REMOTE_FOLDER_EXISTS_CMD = REMOTE + PREFIX + FOLDER + PREFIX + EXISTS;
    //команды изменения состояния папок/файлов в локальном хранилище
    private static final String REMOTE_FOLDER_ADD_CMD = REMOTE + PREFIX + FOLDER + PREFIX + ADD;
    private static final String REMOTE_FOLDER_DELETE_CMD = REMOTE + PREFIX + FOLDER + PREFIX + DELETE;
    private static final String REMOTE_FILE_DELETE_CMD = REMOTE + PREFIX + FILE + PREFIX + LIST;
    private static final String REMOTE_FILE_ADD_CMD = REMOTE + PREFIX + FILE + PREFIX + ADD;
    private static final String LOCAL_FOLDER_ADD_CMD = LOCAL + PREFIX + FOLDER + PREFIX + ADD;
    private static final String LOCAL_FOLDER_DELETE_CMD = LOCAL + PREFIX + FOLDER + PREFIX + DELETE;
    private static final String LOCAL_FILE_DELETE_CMD = LOCAL + PREFIX + FILE + PREFIX + DELETE;
    private static final String LOCAL_FILE_ADD_CMD = LOCAL + PREFIX + FILE + PREFIX + ADD;
    //команды вывода содержимго файлов в консоль //todo: <- реализовать
    private static final String LOCAL_FILE_READ_CMD = LOCAL + PREFIX + FILE + PREFIX + READ;
    private static final String REMOTE_FILE_READ_CMD = REMOTE + PREFIX + FILE + PREFIX + READ;


    // сокращения кусков комманд
    // remote -> rmt
    // local -> lcl
    // file -> fl
    // folder -> fd
    // delete -> dlt
    //read -> rd
    // exists -> ext
    // list - lst
    // Эти не обязательно
    // exit -> ex
    // help -> hlp
    // на случай желания замены части комманды, не надо будет переписывать все комманды.


    // todo: добавить сокращения коммандам
    // сокращенные комманды:
    private static final String HELP_CMD_SRT = HELP_SHRT;
    private static final String EXIT_CMD_SRT = EXIT_SHRT;
    private static final String LOCAL_FILE_LIST_CMD_SRT = LOCAL_SHRT + PREFIX + FILE_SHRT + PREFIX + LIST_SHRT;
    private static final String LOCAL_FILE_EXISTS_CMD_SRT = LOCAL_SHRT + PREFIX + FILE_SHRT + PREFIX + EXISTS_SHRT;
    private static final String LOCAL_FOLDER_LIST_CMD_SRT = LOCAL_SHRT + PREFIX + FOLDER_SHRT + PREFIX + LIST_SHRT;
    private static final String LOCAL_FOLDER_EXISTS_CMD_SRT = LOCAL_SHRT + PREFIX + FOLDER_SHRT + PREFIX + EXISTS_SHRT;
    private static final String REMOTE_FILE_LIST_CMD_SRT = REMOTE_SHRT + PREFIX + FILE_SHRT + PREFIX + LIST_SHRT;
    private static final String REMOTE_FILE_EXISTS_CMD_SRT = REMOTE_SHRT + PREFIX + FILE_SHRT + PREFIX + EXISTS_SHRT;
    private static final String REMOTE_FOLDER_LIST_CMD_SRT = REMOTE_SHRT + PREFIX + FOLDER_SHRT + PREFIX + LIST_SHRT;
    private static final String REMOTE_FOLDER_EXISTS_CMD_SRT = REMOTE_SHRT + PREFIX + FOLDER_SHRT + PREFIX + EXISTS_SHRT;
    private static final String REMOTE_FOLDER_ADD_CMD_SRT = REMOTE_SHRT + PREFIX + FOLDER_SHRT + PREFIX + ADD;
    private static final String REMOTE_FOLDER_DELETE_CMD_SRT = REMOTE_SHRT + PREFIX + FOLDER_SHRT + PREFIX + DELETE_SHRT;
    private static final String REMOTE_FILE_DELETE_CMD_SRT = REMOTE_SHRT + PREFIX + FILE_SHRT + PREFIX + LIST_SHRT;
    private static final String REMOTE_FILE_ADD_CMD_SRT = REMOTE_SHRT + PREFIX + FILE_SHRT + PREFIX + ADD;
    private static final String LOCAL_FOLDER_ADD_CMD_SRT = LOCAL_SHRT + PREFIX + FOLDER_SHRT + PREFIX + ADD;
    private static final String LOCAL_FOLDER_DELETE_CMD_SRT = LOCAL_SHRT + PREFIX + FOLDER_SHRT + PREFIX + DELETE_SHRT;
    private static final String LOCAL_FILE_DELETE_CMD_SRT = LOCAL_SHRT + PREFIX + FILE_SHRT + PREFIX + DELETE_SHRT;
    private static final String LOCAL_FILE_ADD_CMD_SRT = LOCAL_SHRT + PREFIX + FILE_SHRT + PREFIX + ADD;
    private static final String LOCAL_FILE_READ_CMD_SRT = LOCAL_SHRT + PREFIX + FILE_SHRT + PREFIX + READ_SHRT;
    private static final String REMOTE_FILE_READ_CMD_SRT = REMOTE_SHRT + PREFIX + FILE_SHRT + PREFIX + READ_SHRT;

    @Inject
    private AppService appService;
    @Inject
    private FolderLocalService folderLocalService;
    @Inject
    private FileLocalService fileLocalService;
    @Inject
    private FolderRemoteService folderRemoteService;
    @Inject
    private FileRemoteService fileRemoteService;
    @Inject
    private Event<KeyboardCommandEvent> keyboardCommandEvent;

    public void observe(@Observes final KeyboardCommandEvent event) {
        System.out.println("\ncmd:");
        final Scanner scanner = new Scanner(System.in);
        String command = scanner.next();// nextLine() вместо next(), чтобы была возможность реализовать функионал
        // создания запроса с доп. параметрами(например имя).
        //todo: создать switch, в котором, все методы будут вызываться, используя константы
        switch (command) {
            case HELP_CMD:
                printHelp();
                break;
            case HELP_CMD_SRT:
                printHelp();
                break;
            case ABBREVIATIONS_CMD:
                printAbbr();
                break;
            case EXIT_CMD:
                appService.shutdown();
                break;
            case EXIT_CMD_SRT:
                appService.shutdown();
                break;
            // todo: добавить сокр. для login и logout
            case LOGIN_CMD:
                appService.login();
                break;
            case LOGOUT_CMD:
                appService.logout();
                break;

            // local/remote file add commands
            case LOCAL_FILE_ADD_CMD:
                addFile(0);
                break;
            case LOCAL_FILE_ADD_CMD_SRT:
                addFile(0);
                break;
            case REMOTE_FILE_ADD_CMD:
                addFile(1);
                break;
            case REMOTE_FILE_ADD_CMD_SRT:
                addFile(1);
                break;
            // folder add, delete, exists,list commands
            // local
            case LOCAL_FOLDER_LIST_CMD:
                folderLocalService.getListNamesRoot();
                break;
            case LOCAL_FOLDER_LIST_CMD_SRT:
                folderLocalService.getListNamesRoot();
                break;
            case LOCAL_FOLDER_ADD_CMD:
                localFolderServiceCmd(0);
                break;
            case LOCAL_FOLDER_ADD_CMD_SRT:
                localFolderServiceCmd(0);
                break;
            case LOCAL_FOLDER_DELETE_CMD:
                localFolderServiceCmd(1);
                break;
            case LOCAL_FOLDER_DELETE_CMD_SRT:
                localFolderServiceCmd(1);
                break;
            case LOCAL_FOLDER_EXISTS_CMD:
                localFolderServiceCmd(2);
                break;
            case LOCAL_FOLDER_EXISTS_CMD_SRT:
                localFolderServiceCmd(2);
                break;
            //remote
            // todo: закончить реализацию remoteServices
            case REMOTE_FOLDER_LIST_CMD:
                folderRemoteService.getListNamesRoot();
                break;
            case REMOTE_FOLDER_LIST_CMD_SRT:
                folderRemoteService.getListNamesRoot();
                break;
            // file delete, exists, list commands
            case LOCAL_FILE_LIST_CMD:
                fileLocalService.getRootFiles();
                break;
            case LOCAL_FILE_LIST_CMD_SRT:
                fileLocalService.getRootFiles();
                break;
            case LOCAL_FILE_DELETE_CMD:
                localFileServiceCmd(0);
                break;
            case LOCAL_FILE_DELETE_CMD_SRT:
                localFileServiceCmd(0);
                break;
            case LOCAL_FILE_EXISTS_CMD:
                localFileServiceCmd(1);
                break;
            case LOCAL_FILE_EXISTS_CMD_SRT:
                localFileServiceCmd(1);
                break;
            case GET_REPO_CMD:
                System.out.println(appService.repository());
                break;
            case GET_SESSION_CMD:
                System.out.println(appService.session());
                break;
            default:
                System.out.println("Команда не опознана");
        }
        keyboardCommandEvent.fire(new KeyboardCommandEvent());
    }


    private void addFile(int i) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter file\'s name");
        String name = scr.next();
        System.out.println("Enter file\'s data");
        scr.nextLine();
        String data = scr.nextLine();
        switch (i) {
            case 0:
                fileLocalService.createRootFile(name, data);
                break;
            case 1:
                fileRemoteService.createRootFile(name, data);
                break;
            default:
                System.out.println("System error.");
        }
    }

    // TODO: реализовать функционал, при котором, сразу после комманды можно было бы писать имя файла
    private void localFileServiceCmd(int i) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter file/folder\'s name");
        String name = scr.next();
        switch (i) {
            case 0:
                fileLocalService.remove(name);
                break;
            case 1:
                System.out.println(fileLocalService.exists(name));
                break;
            default:
                System.out.println("Системная ошибка.");

        }

    }

    private void localFolderServiceCmd(int i) {
        Scanner scr = new Scanner(System.in);
        System.out.println("Enter file/folder\'s name");
        String name = scr.next();
        switch (i) {
            case 0:
                folderLocalService.createFolder(name);
                break;
            case 1:
                folderLocalService.deleteFolder(name);
                break;
            case 2:
                System.out.println(folderLocalService.exists(name));
                break;
            default:
                System.out.println("Системная ошибка.");

        }

    }

    private void printHelp() {
        System.out.println("//////////////////////////////////////////////////////////");
        System.out.println(HELP_CMD + " - команда вызова списка команд и их описания");
        System.out.println(ABBREVIATIONS_CMD + "- команда вывода списка стандартных сокращений отдельных частей комманд");
        System.out.println(EXIT_CMD + " - команда выхода из приложения");
        System.out.println(LOGIN_CMD + " - команда регистрирования на удаленное хранилище");
        System.out.println(LOGOUT_CMD + " - команда выхода из учетной записи удаленного хранилища");
        System.out.println(LOCAL_FILE_LIST_CMD + " - команда возращающая лист файлов из локальной папки");
        System.out.println(LOCAL_FOLDER_LIST_CMD + " - команда возращающая лист папок из локальной папки");
        System.out.println(REMOTE_FILE_LIST_CMD + " - команда возращающая лист файлов из папки удаленного хранилища");
        System.out.println(REMOTE_FOLDER_LIST_CMD + " - команда возращающая лист папок из папки удаленного хранилища");
        System.out.println(LOCAL_FILE_EXISTS_CMD + " - команда проверки существования файла в локальной папке");
        System.out.println(LOCAL_FOLDER_EXISTS_CMD + " - команда проверки существования папки в локальной папке");
        System.out.println(REMOTE_FILE_EXISTS_CMD + " - команда проверки существования файла в удаленной папке");
        System.out.println(REMOTE_FOLDER_EXISTS_CMD + " - команда проверки существования папки в удаленной папке");
        System.out.println(LOCAL_FILE_READ_CMD + " - команда чтения содержимого файла в локальной папке");
        System.out.println(REMOTE_FILE_READ_CMD + " - команда чтения содержимого файла в удаленной папке");
        System.out.println(LOCAL_FOLDER_ADD_CMD + " - команда создания папки в локальной папке");
        System.out.println(LOCAL_FILE_ADD_CMD + " - команда создания файла в локальной папке");
        System.out.println(LOCAL_FOLDER_DELETE_CMD + " - команда удаления папки из локальной папки");
        System.out.println(LOCAL_FILE_DELETE_CMD + " - команда удаления файла из локальной папки");
        System.out.println(REMOTE_FOLDER_ADD_CMD + " - команда создания папки в удаленной папке");
        System.out.println(REMOTE_FILE_ADD_CMD + " - команда создания файла в удаленной папке");
        System.out.println(REMOTE_FOLDER_DELETE_CMD + " - команда удаления папки из удаленной папки");
        System.out.println(REMOTE_FILE_DELETE_CMD + " - команда удаления файла из удаленной папки");
    }

    private void printAbbr() {
        // remote -> rmt
        // local -> lcl
        // file -> fl
        // folder -> fd
        // delete -> dlt
        //read -> rd
        // exists -> ext
        // list - lst
        // Эти не обязательно
        // exit -> ex
        // help -> hlp
        System.out.println(REMOTE + ": " + REMOTE_SHRT);
        System.out.println(LOCAL + ": " + LOCAL_SHRT);
        System.out.println(FILE + ": " + FILE_SHRT);
        System.out.println(FOLDER + ": " + FOLDER_SHRT);
        System.out.println(DELETE + ": " + DELETE_SHRT);
        System.out.println(READ + ": " + READ_SHRT);
        System.out.println(EXISTS + ": " + EXISTS_SHRT);
        System.out.println(LIST + ": " + LIST_SHRT);
        System.out.println(EXIT + ": " + EXIT_SHRT);
        System.out.println(HELP + ": " + HELP_SHRT);
    }
}