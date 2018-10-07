package ru.timokhin.Cloud.service.remote;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.timokhin.Cloud.api.remote.FolderRemoteService;
import ru.timokhin.Cloud.system.AppService;
import ru.timokhin.Cloud.system.SettingService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeType;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FolderRemoteServiceBean implements FolderRemoteService {
    @Inject
    private SettingService settingService;
    @Inject
    private AppService appService;

    @Override
    public void init() {
    }

    @Override
    public @NotNull List<String> getListFolderName() {
        List<String> folders = new ArrayList<>();
        if(appService.getRootNode() == null)return folders;
        Node root = appService.getRootNode();
        try {
            NodeIterator ndi = root.getNodes();
            while (ndi.hasNext()){
                Node node = ndi.nextNode();
                NodeType nodeType = node.getPrimaryNodeType();
                boolean isFolder = nodeType.isNodeType("nt:folder");
                if(isFolder)
                    folders.add(node.getName());
            }
            return folders;
        } catch (RepositoryException e) {
            System.out.println("root folder is empty");
            return folders;
        }

    }

    @Override
    public void getListNamesRoot() {
        for (String name: getListFolderName()){
            System.out.println(name);
        }
    }

    @Override
    public void createFolder(@Nullable String folderName) {

    }

    @Override
    public boolean deleteFolder(@Nullable String folderName) {
        return true;
    }

    @Override
    public boolean clearRoot() {
        return false;
    }

    @Override
    public boolean exists(@Nullable String folderName) {
        return false;
    }
}
