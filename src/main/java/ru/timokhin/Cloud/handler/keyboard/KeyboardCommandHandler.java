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
    //команда вызова списка команд и их описания
    private static final String HELP_CMD = "help";
    // команда выхода из приложения
    private static final String EXIT_CMD = "exit";
    //команды входа/выхода из учетной записи
    private static final String LOGIN_CMD = "login";
    private static final String LOGOUT_CMD = "logout";
    // команды поиска элементов по локальному и удаленному хранилищу(не реализованно)
    private static final String LOCAL_FILE_LIST_CMD = "local-file-list";
    private static final String LOCAL_FILE_EXISTS_CMD = "local-file-exists";
    private static final String LOCAL_FOLDER_LIST_CMD = "local-folder-list";
    private static final String LOCAL_FOLDER_EXISTS_CMD = "local-folder-exists";
    private static final String REMOTE_FILE_LIST_CMD = "remote-file-list";
    private static final String REMOTE_FILE_EXISTS_CMD = "remote-file-exists";
    private static final String REMOTE_FOLDER_LIST_CMD = "remote-folder-list";
    private static final String REMOTE_FOLDER_EXISTS_CMD = "remote-folder-exists";

    //команды изменения состояния папок/файлов в удаленом хранилище
    private static final String REMOTE_FOLDER_ADD_CMD = "remote-folder-add";
    private static final String REMOTE_FOLDER_DELETE_CMD = "remote-folder-delete";
    private static final String REMOTE_FILE_DELETE_CMD = "remote-file-delete";
    private static final String REMOTE_FILE_ADD_CMD = "remote-file-add";
    //команды изменения состояния папок/файлов в локальном хранилище
    private static final String LOCAL_FOLDER_ADD_CMD = "local-folder-add";
    private static final String LOCAL_FOLDER_DELETE_CMD = "local-folder-delete";
    private static final String LOCAL_FILE_DELETE_CMD = "local-file-delete";
    private static final String LOCAL_FILE_ADD_CMD = "local-file-add";
    //команды вывода содержимго файлов в консоль //todo: <- реализовать
    private static final String LOCAL_FILE_READ_CMD = "local-file-read";
    private static final String REMOTE_FILE_READ_CMD = "remote-file-read";

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
            case EXIT_CMD:
                appService.shutdown();
                break;
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
            case REMOTE_FILE_ADD_CMD:
                addFile(1);
                break;
            // folder add, delete, exists,list commands
            // local
            case LOCAL_FOLDER_LIST_CMD:
                folderLocalService.getListNamesRoot();
                break;
            case LOCAL_FOLDER_ADD_CMD:
                localFolderServiceCmd(0);
                break;
            case LOCAL_FOLDER_DELETE_CMD:
                localFolderServiceCmd(1);
                break;
            case LOCAL_FOLDER_EXISTS_CMD:
                localFolderServiceCmd(2);
                break;
            //remote
            case REMOTE_FOLDER_LIST_CMD:
                folderRemoteService.getListNamesRoot();
                break;
            // file delete, exists, list commands
            case LOCAL_FILE_LIST_CMD:
                fileLocalService.getRootFiles();
                break;
            case LOCAL_FILE_DELETE_CMD:
                localFileServiceCmd(0);
                break;
            case LOCAL_FILE_EXISTS_CMD:
                localFileServiceCmd(1);
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
}