package io.yousefessa.robotimer.update.handler.factory;

import io.yousefessa.robotimer.update.handler.DefaultUpdateHandler;
import io.yousefessa.robotimer.update.handler.UpdateHandler;

public class DefaultUpdateHandlerFactory implements UpdateHandlerFactory {
    @Override
    public UpdateHandler createUpdaterHandler() {
        return new DefaultUpdateHandler();
    }
}
