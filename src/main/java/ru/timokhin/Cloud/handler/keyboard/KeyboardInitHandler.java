package ru.timokhin.Cloud.handler.keyboard;

import ru.timokhin.Cloud.event.keyboard.KeyboardCommandEvent;
import ru.timokhin.Cloud.event.keyboard.KeyboardInitEvent;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class KeyboardInitHandler {
    @Inject
    private Event<KeyboardCommandEvent> keyboardCommandEvent;
    /**
     * получив {@link KeyboardInitEvent} из {@link ru.timokhin.Cloud.service.system.BootstrapServiceBean} , выводится приветственное сообщение и {@link KeyboardCommandEvent} срабатывает в {@linkplain KeyboardCommandHandler}**/

    public void observe(@Observes final KeyboardInitEvent event){
        System.out.println();
        System.out.println("WELCOME TO COMMAND LINE INTERFACE");
        System.out.println("FOR LIST OF COMMANDS ENTER COMMAND \"help\"");
        keyboardCommandEvent.fire(new KeyboardCommandEvent());
    }
}
