package kr.co.yigil.decorator;

import java.util.Map;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

public class MdcDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        final Map<String, String> contextMap = MDC.getCopyOfContextMap();
        return () -> {
            if(contextMap != null) MDC.setContextMap(contextMap);
            runnable.run();
        };
    }

}
