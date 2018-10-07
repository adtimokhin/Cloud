package ru.timokhin.Cloud.service.system;

import ru.timokhin.Cloud.api.annotations.Loggable;
import ru.timokhin.Cloud.event.keyboard.KeyboardInitEvent;
import ru.timokhin.Cloud.service.local.FolderLocalServiceBean;
import ru.timokhin.Cloud.system.AppService;
import ru.timokhin.Cloud.system.SettingService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@ApplicationScoped
public class BootstrapServiceBean {
    @Inject
    private Event<KeyboardInitEvent> keyboardInitEventEvent;

    @Inject
    private AppService appService;
    @Inject
    private SettingService settingService;
    @Inject
    private FolderLocalServiceBean folderLocalServiceBean;


    /**
     * {@linkplain SettingServiceBean} сервис для работы с resources.properties
     * {@linkplain AppServiceBean} сервис для работы с клиентом. Вход в репозиторий, выход итд
     * {@linkplain FolderLocalServiceBean} сервис для создания папок на локальной машине пользователя
     * {@linkplain ru.timokhin.Cloud.handler.keyboard.KeyboardInitHandler} ловит {@link KeyboardInitEvent}
     * **/
    @Loggable
    public void init() {
        settingService.init();
        appService.init();
        folderLocalServiceBean.init();
        keyboardInitEventEvent.fire(new KeyboardInitEvent());

    }
}
