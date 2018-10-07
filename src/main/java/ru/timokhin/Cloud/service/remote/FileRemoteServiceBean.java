package ru.timokhin.Cloud.service.remote;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.timokhin.Cloud.api.remote.FileRemoteService;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.util.List;

@ApplicationScoped
public class FileRemoteServiceBean implements FileRemoteService {
    // Todo: реализовать все методы. Все есть в 3-ем уроке
    @Override
    public @NotNull List<String> getListNameFiles() {
        return null;
    }

    @Nullable
    @Override
    public byte[] readData(@Nullable String fileName) {
        return new byte[0];
    }

    @Override
    public boolean exists(@Nullable String fileName) {
        return false;
    }

    @Override
    public void remove(@Nullable String fileName) {

    }

    @Override
    public void createRootFile(@Nullable String fileName, String inputData) {

    }

    @Override
    public void writeData(@Nullable File file, @Nullable byte[] data) {

    }

    @Override
    public void getRootFiles() {

    }
}
